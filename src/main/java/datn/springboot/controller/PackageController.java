    package datn.springboot.controller;

    import com.mongodb.DuplicateKeyException;
    import datn.springboot.entity.Block;
    import datn.springboot.entity.Order;
    import datn.springboot.entity.Package;
    import datn.springboot.entity.PackageStatus;
    import datn.springboot.entity.dto.PackageDTO;
    import datn.springboot.repo.BlockRepository;
    import datn.springboot.repo.OrderRepository;
    import datn.springboot.repo.PackageRepository;
    import datn.springboot.service.PackageService;
    import datn.springboot.websocket.PackageNotifier;
    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.text.SimpleDateFormat;
    import java.util.*;

    @RestController
    @RequestMapping("/api/packages")
    public class PackageController {
        private final PackageService PackageService;
        private final PackageRepository packageRepository;
        private final PackageNotifier packageNotifier;
        private final BlockRepository blockRepository;
        private final OrderRepository orderRepository;

        @Autowired
        public PackageController(PackageService PackageService, PackageRepository packageRepository, PackageNotifier packageNotifier, BlockRepository blockRepository, OrderRepository orderRepository) {
            this.PackageService = PackageService;
            this.packageRepository = packageRepository;
            this.packageNotifier = packageNotifier;
            this.blockRepository = blockRepository;
            this.orderRepository = orderRepository;
        }

        @PostMapping("/init")
        public ResponseEntity<?> initPackage(@RequestBody Package pkg, HttpServletRequest request) {
//            System.out.println("📥 [Controller] INIT received: " + pkg.getRfid() + " from IP=" + request.getRemoteAddr());
            String requestId = request.getRequestId(); // nếu bạn định tạo field riêng thì lấy từ JSON
            System.out.println("📥 INIT received: RFID=" + pkg.getRfid() + ", RequestID=" + requestId + ", From IP=" + request.getRemoteAddr()+", Z:"+pkg.getZone());
            // Gán các trường mặc định
            pkg.setTime_in(new Date().toString());
            pkg.setStatus(PackageStatus.IMPORTED);  // hoặc PENDING nếu chưa hoàn tất

            try {
                // Lưu vào MongoDB
                Package saved = packageRepository.save(pkg);

                // Gửi WebSocket thông báo cho frontend
                packageNotifier.notifyNewPackage(saved);

                return ResponseEntity.ok(saved);
            } catch (DuplicateKeyException e) {
                System.out.println("⚠️ RFID already exists: " + pkg.getRfid());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("❌ RFID đã tồn tại: " + pkg.getRfid());
            } catch (Exception e) {
                System.out.println("❌ Error saving package: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("🚨 Internal server error");
            }
        }


        @PostMapping("/finalize")
        public ResponseEntity<?> finalizePackage(@RequestBody Package pkg) {
            Order order = orderRepository.findOrderById(pkg.getPoId());
            System.out.println("📥 [Finalize] Received: " + pkg.getRfid() + ", PO=" + pkg.getPoId() + ", Product=" + pkg.getProductId());

            List<Package> listEx = packageRepository.findAllByRfid(pkg.getRfid());
//            if (listEx.size() >= 2) {
//                packageRepository.delete(listEx.get(0));
//            }
            Package existing = packageRepository.findByRfid(pkg.getRfid());
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy gói hàng với RFID: " + pkg.getRfid());
            }

            // Gán các giá trị được nhập từ Web
            existing.setPoId(pkg.getPoId());
            existing.setProductId(pkg.getProductId());

            if (existing.getTime_in() == null || existing.getTime_in().isEmpty()) {
                existing.setTime_in(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }

            // ❌ KHÔNG override nếu đã ON_SHELF hoặc EXPORTED
            if (existing.getStatus() != PackageStatus.ON_SHELF && existing.getStatus() != PackageStatus.EXPORTED) {
                existing.setStatus(PackageStatus.IMPORTED);
            }

            // Cập nhật Order
            if(order != null){
                if (order.getProductIdList() == null){
                    order.setProductIdList(new ArrayList<>());
                }
                order.getProductIdList().add(pkg.getProductId());
                orderRepository.save(order);
            } else {
                List<String> prdIds = new ArrayList<>();
                prdIds.add(pkg.getProductId());
                order = new Order();
                order.setId(pkg.getPoId());
                order.setProductIdList(prdIds);
                orderRepository.save(order);
            }

            Package saved = packageRepository.save(existing);
            return ResponseEntity.ok(saved);
        }

        @PostMapping("/inbound")
        public ResponseEntity<?> inboundPackage(@RequestBody Map<String, String> payload) {
            String rfid = payload.get("rfid");
            String blockId = payload.get("block");

            System.out.println("📥 [Inbound] Nhận gói hàng RFID=" + rfid + ", block=" + blockId);

            Package pkg = packageRepository.findByRfid(rfid);
            if (pkg == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy gói hàng RFID: " + rfid);
            }

            // Nếu chưa được lưu kho (status null hoặc IMPORTED)
            if (pkg.getStatus() != PackageStatus.ON_SHELF && pkg.getStatus() != PackageStatus.EXPORTED) {
                pkg.setStatus(PackageStatus.ON_SHELF);
                pkg.setBlock(blockId);
                pkg.setTime_in(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }

            // Cập nhật block chứa
            Block block = blockRepository.findById(blockId).orElse(null);
            if (block != null) {
                block.setHasPackage(true);
                block.setRfid(pkg.getRfid());
                block.setBeaconName(pkg.getBeacon_name());
                blockRepository.save(block);
            }

            Package saved = packageRepository.save(pkg);
            return ResponseEntity.ok(saved);
        }

        @PostMapping
        public ResponseEntity<?> createPackage(@RequestBody Package Package, HttpServletRequest request) {
            Package ex = packageRepository.findByRfid(Package.getRfid());
            if (ex != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("RFID:" +Package.getRfid() +"existed");        }
            System.out.println("📥 [Controller default] POST received: " + Package.getRfid() + " from IP=" + request.getRemoteAddr());
            Package.setTime_in(String.valueOf(new Date()));
            return ResponseEntity.ok(PackageService.savePackage(Package));
        }

        @GetMapping
        public ResponseEntity<?> getAllPackages() {
            try {
                List<Package> packages = packageRepository.findAll();

                for (Package pkg : packages) {
                    System.out.println("✅ package: " + pkg.getRfid() + " | status=" + pkg.getStatus());
                }

                return ResponseEntity.ok(packages);
            } catch (Exception e) {
                e.printStackTrace(); // Sẽ in lỗi cụ thể lên Heroku logs
                return ResponseEntity.status(500).body("❌ ERROR: " + e.getMessage());
            }
        }



        @GetMapping("/{id}")
        public ResponseEntity<Package> getPackageById(@PathVariable String id) {
            return PackageService.getPackageById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<Package> updatePackage(@PathVariable String id, @RequestBody Package input) {
            return PackageService.getPackageById(id)
                    .map(pkg -> {
                        pkg.setPoId(input.getPoId());
                        pkg.setProductId(input.getProductId());
                        System.out.println("UPDATE NORMAL IS CALLED WITH" + input.getId());
                        return ResponseEntity.ok(PackageService.savePackage(pkg));
                    })
                    .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/update/{rfid}")
        public ResponseEntity<?> updatePackageByRfid(@PathVariable String rfid, @RequestBody PackageDTO incoming) {
            System.out.println("📥 [DTO] status = " + incoming.getStatus());

            Package pkg = packageRepository.findByRfid(rfid);
            if (pkg == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found");
            }

            // === CASE 1: OUTBOUND (có time_out) ===
            if (incoming.getTime_out() != null && !incoming.getTime_out().isEmpty()) {
                pkg.setTime_out(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                pkg.setStatus(PackageStatus.EXPORTED);
                pkg.setRfid("");  // clear
                pkg.setBeacon_name("");

                Block block = blockRepository.findById(pkg.getBlock()).orElse(null);
                if (block != null) {
                    block.setHasPackage(false);
                    block.setRfid("");
                    block.setBeaconName("");
                    blockRepository.save(block);
                }

                return ResponseEntity.ok(packageRepository.save(pkg));
            }

            // === CASE 2: INBOUND ===
            if (incoming.getTime_in() != null && !incoming.getTime_in().isEmpty()) {
                // Nếu status hiện tại chưa là ON_SHELF hoặc EXPORTED thì cập nhật
                if (pkg.getStatus() != PackageStatus.ON_SHELF && pkg.getStatus() != PackageStatus.EXPORTED) {
                    if (incoming.getStatus() != null && !incoming.getStatus().isEmpty()) {
                        try {
                            PackageStatus newStatus = PackageStatus.valueOf(incoming.getStatus().toUpperCase());
                            pkg.setStatus(newStatus);
                            System.out.println("🔄 Updated status to: " + newStatus);
                        } catch (IllegalArgumentException e) {
                            System.out.println("⚠️ Invalid status value received: " + incoming.getStatus());
                        }
                    }
                }

                pkg.setTime_in(incoming.getTime_in());
                pkg.setBlock(incoming.getBlock());
                pkg.setBeacon_name(incoming.getBeacon_name());

                if (incoming.getPoId() != null) pkg.setPoId(incoming.getPoId());
                if (incoming.getProductId() != null) pkg.setProductId(incoming.getProductId());

                Block block = blockRepository.findById(pkg.getBlock()).orElse(null);
                if (block != null) {
                    block.setHasPackage(true);
                    block.setRfid(pkg.getRfid());
                    block.setBeaconName(pkg.getBeacon_name());
                    blockRepository.save(block);
                }

                // xử lý order nếu có poId + productId
                if (pkg.getPoId() != null && pkg.getProductId() != null) {
                    Order order = orderRepository.findOrderById(pkg.getPoId());
                    if (order == null) {
                        order = new Order();
                        order.setId(pkg.getPoId());
                        order.setProductIdList(new ArrayList<>());
                        order.setBlockIdList(new ArrayList<>());
                    }

                    if (!order.getProductIdList().contains(pkg.getProductId())) {
                        order.getProductIdList().add(pkg.getProductId());
                    }

                    if (!order.getBlockIdList().contains(pkg.getBlock())) {
                        order.getBlockIdList().add(pkg.getBlock());
                    }

                    orderRepository.save(order);
                }

                return ResponseEntity.ok(packageRepository.save(pkg));
            }

            return ResponseEntity.badRequest().body("❌ Thiếu time_in hoặc time_out hợp lệ.");
        }




        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePackage(@PathVariable String id) {
            PackageService.deletePackage(id);
            System.out.println("DELETE IS CALLED");
            return ResponseEntity.noContent().build();
        }
    }