package datn.springboot.controller;

import datn.springboot.entity.Package;
import datn.springboot.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Packages")
public class PackageController {
    private final PackageService PackageService;

    @Autowired
    public PackageController(PackageService PackageService) {
        this.PackageService = PackageService;
    }

    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody Package Package) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        PackageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}