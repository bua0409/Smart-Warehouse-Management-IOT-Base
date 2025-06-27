package datn.springboot.controller;

import datn.springboot.entity.Block;
import datn.springboot.entity.Order;
import datn.springboot.entity.Package;
import datn.springboot.entity.PackageStatus;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        Package ex = packageRepository.findByRfid(pkg.getRfid());
        if (ex != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("RFID:" + pkg.getRfid() + " existed");
        }
        System.out.println("📥 [Controller] INIT received: " + pkg.getRfid() + " from IP=" + request.getRemoteAddr());
        packageNotifier.notifyNewPackage(pkg); // ⬅ Gửi WebSocket
        return ResponseEntity.ok(pkg);
    }

    @PostMapping("/finalize")
    public ResponseEntity<?> finalizePackage(@RequestBody Package pkg) {
        pkg.setTime_in(String.valueOf(new Date()));
        Order order = orderRepository.findOrderById(pkg.getPoId());
        if(order != null){
            if (order.productIdList == null){
                order.setProductIdList(new ArrayList<>());
            }
            order.productIdList.add(pkg.getProductId());
            orderRepository.save(order);
        }else {
            List<String> prdIds = new ArrayList<>();
            prdIds.add(pkg.getProductId());
            order = new Order();
            order.setId(pkg.getPoId());
            order.setProductIdList(prdIds);
            orderRepository.save(order);
        }
        return ResponseEntity.ok(PackageService.savePackage(pkg));
    }

    @PostMapping
    public ResponseEntity<?> createPackage(@RequestBody Package Package, HttpServletRequest request) {
        Package ex = packageRepository.findByRfid(Package.getRfid());
        if (ex != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("RFID:" +Package.getRfid() +"existed");        }
        System.out.println("📥 [Controller] POST received: " + Package.getRfid() + " from IP=" + request.getRemoteAddr());
        Package.setTime_in(String.valueOf(new Date()));
        return ResponseEntity.ok(PackageService.savePackage(Package));
    }

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        return ResponseEntity.ok(PackageService.getAllPackages());
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
                    return ResponseEntity.ok(PackageService.savePackage(pkg));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{rfid}")
    public ResponseEntity<?> updatePackageByRfid(@PathVariable String rfid, @RequestBody Package incomingPkg) {
        Package pkg = packageRepository.findByRfid(rfid);
        if (pkg == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found");

        if (incomingPkg.getTime_out() != null) {
            // ======= XUẤT KHO =======
            pkg.setTime_out(String.valueOf(new Date()));
            pkg.setStatus(PackageStatus.EXPORTED);
            pkg.setRfid("");  // reset thông tin
            pkg.setBeacon_name("");

            Block block = blockRepository.findById(pkg.getBlock()).orElse(null);
            if (block != null) {
                block.setHasPackage(false);
                block.setRfid("");
                block.setBeaconName("");
                blockRepository.save(block);
            }
        } else {
            // ======= LƯU KHO =======
            pkg.setBlock(incomingPkg.getBlock());
            pkg.setZone(incomingPkg.getZone());
            pkg.setStatus(PackageStatus.ON_SHELF);
            Block block = blockRepository.findById(incomingPkg.getBlock()).orElse(null);
            Order order = orderRepository.findOrderById(pkg.getPoId());
            if (order.blockIdList != null){
                order.blockIdList.add(pkg.getBlock());
            } else {
                order.setBlockIdList(new ArrayList<>());
                order.blockIdList.add(pkg.getBlock());
            }
            orderRepository.save(order);
            if (block != null) {
                block.setHasPackage(true);
                block.setRfid(pkg.getRfid());
                block.setBeaconName(pkg.getBeacon_name());
                blockRepository.save(block);
            }
        }

        Package saved = packageRepository.save(pkg);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        PackageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}