package datn.springboot.service;

import datn.springboot.entity.Package;

import java.util.List;
import java.util.Optional;


public interface PackageService {
    Package savePackage(Package Package);
    List<Package> getAllPackages();
    Optional<Package> getPackageById(String id);
    Package updatePackage(String id, Package Package);
    void deletePackage(String id);
    Package updatePackageByRfid(String rfid, Package Package);
    Optional<Package> getPackageByRfid(String rfid);
}
