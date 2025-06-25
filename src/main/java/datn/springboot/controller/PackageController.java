package datn.springboot.controller;

import datn.springboot.entity.Package;
import datn.springboot.repo.PackageRepository;
import datn.springboot.service.PackageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
public class PackageController {
    private final PackageService PackageService;
    private final PackageRepository packageRepository;

    @Autowired
    public PackageController(PackageService PackageService, PackageRepository packageRepository) {
        this.PackageService = PackageService;
        this.packageRepository = packageRepository;
    }

    @PostMapping
    public ResponseEntity<?> createPackage(@RequestBody Package Package, HttpServletRequest request) {
        Package ex = packageRepository.findByRfid(Package.getRfid());
        if (ex != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("RFID:" +Package.getRfid() +"existed");        }
        System.out.println("📥 [Controller] POST received: " + Package.getRfid() + " from IP=" + request.getRemoteAddr());
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
    public ResponseEntity<Package> updatePackage(@PathVariable String id, @RequestBody Package Package) {
        return ResponseEntity.ok(PackageService.updatePackage(id, Package));
    }

    @PutMapping("/update/{rfid}")
    public ResponseEntity<Package> updatePackageByRfid(@PathVariable String rfid, @RequestBody Package Package) {
        return ResponseEntity.ok(PackageService.updatePackageByRfid(rfid, Package));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        PackageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}