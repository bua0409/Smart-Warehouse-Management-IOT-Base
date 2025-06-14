package datn.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import datn.springboot.entity.Package;
import datn.springboot.repo.PackageRepository;

import java.util.List;
import java.util.Optional;


public interface packageService {
    Package savePackage(Package aPackage);
    List<Package> getAllPackages();
    Optional<Package> getPackageById(String id);
    Package updatePackage(String id, Package aPackage);
    void deletePackage(String id);
}
