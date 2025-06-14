package datn.springboot.service;

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
    public Package updatePackage(String id, Package Package) {
        Package.setRfid(id);
        return PackageRepository.save(Package);
    }

    @Override
    public void deletePackage(String id) {
        PackageRepository.deleteById(id);
    }
}
