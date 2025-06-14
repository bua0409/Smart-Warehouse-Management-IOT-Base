package datn.springboot.service;

import datn.springboot.entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import datn.springboot.entity.Package;
import datn.springboot.repo.PackageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class packageServiceImpl implements packageService {
    private final PackageRepository packageRepository;

    @Autowired
    public packageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public Package savePackage(Package aPackage) {
        return packageRepository.save(aPackage);
    }

    @Override
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Package> getPackageById(String id) {
        return packageRepository.findById(id);
    }


    //    @Override
//    public Optional<Package> get?
    @Override
    public Package updatePackage(String id, Package aPackage) {
        return packageRepository.save(aPackage);
    }

    @Override
    public void deletePackage(String id) {
        packageRepository.deleteById(id);
    }
}
