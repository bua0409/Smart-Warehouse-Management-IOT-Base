package datn.springboot.service;

import datn.springboot.controller.PackageController;
import datn.springboot.entity.Package;
import datn.springboot.repo.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {
    private final PackageRepository PackageRepository;

    @Autowired
    public PackageServiceImpl(PackageRepository PackageRepository) {
        this.PackageRepository = PackageRepository;
    }

    @Override
    public Package savePackage(Package Package) {
        return PackageRepository.save(Package);
    }

    @Override
    public List<Package> getAllPackages() {
        return PackageRepository.findAll();
    }

    @Override
    public Optional<Package> getPackageById(String id) {
        return PackageRepository.findById(id);
    }

    @Override
    public Package updatePackage(String rfid, Package Package) {
        return PackageRepository.save(Package);
    }

    @Override
    public void deletePackage(String id) {
        PackageRepository.deleteById(id);
    }

    @Override
    public Package updatePackageByRfid(String rfid, Package Package) {
        Package pack = PackageRepository.findAll().stream().filter(x -> x.getRfid().equals(rfid)).findFirst().orElse(null);
        if (pack != null) {
            pack.setBlock(Package.getBlock());
            pack.setZone(Package.getZone());
            pack.setTime_in(Package.getTime_in());
            return PackageRepository.save(pack);
        }
        return null;
    }

    @Override
    public Optional<Package> getPackageByRfid(String rfid) {
        return Optional.empty();
    }
}
