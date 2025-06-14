package datn.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import datn.springboot.entity.Package;
import datn.springboot.service.packageService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class productController {
    private final packageService packageService;

    @Autowired
    public productController(packageService packageService) {
        this.packageService = packageService;}

    @PostMapping
    public ResponseEntity<Package> createPackage(@RequestBody Package aPackage) {
        return ResponseEntity.ok(packageService.savePackage(aPackage));
    }

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        return ResponseEntity.ok(packageService.getAllPackages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable String id) {
        return packageService.getPackageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable String id, @RequestBody Package aPackage) {
        return ResponseEntity.ok(packageService.updatePackage(id, aPackage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}