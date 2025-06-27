package datn.springboot.controller;

import datn.springboot.entity.Package;
import datn.springboot.entity.PackageStatus;
import datn.springboot.repo.PackageRepository;
import datn.springboot.service.PackageService;
import datn.springboot.websocket.PackageNotifier;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
public class PackageController {
    private final PackageService PackageService;
    private final PackageRepository packageRepository;
    private final PackageNotifier packageNotifier;

    @Autowired
    public PackageController(PackageService PackageService, PackageRepository packageRepository, PackageNotifier packageNotifier) {
        this.PackageService = PackageService;
        this.packageRepository = packageRepository;
        this.packageNotifier = packageNotifier;
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
    public ResponseEntity<Package> updatePackageByRfid(@PathVariable String rfid, @RequestBody Package Package) {
        Optional<Package> packageOptional = packageRepository.findById(rfid);
        if (Package.getTime_out()!=null && packageOptional.isPresent()) {
            Package.setTime_out(String.valueOf(new Date()));
            Package.setRfid("");
            Package.setBeacon_name("");
            Package.setStatus(PackageStatus.EXPORTED);
            Package.setZone(packageOptional.get().getZone());
            Package.setBlock(packageOptional.get().getBlock());
        } else {
            Package.setTime_in(String.valueOf(new Date()));
            Package.setStatus(PackageStatus.ON_SHELF);
        }

        return ResponseEntity.ok(PackageService.updatePackageByRfid(rfid, Package));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        PackageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}