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
    public Package savePackage(Package pkg) {
        Package existing = PackageRepository.findByRfid(pkg.getRfid());
        if (existing != null) return existing; // hoặc throw exception nếu muốn
        return PackageRepository.save(pkg);
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
    public Package updatePackageByRfid(String rfid, Package updated) {
        Package existing = PackageRepository.findByRfid(rfid);
        if (existing == null){
            System.out.println("❌ Không tìm thấy package với RFID: " + rfid);
            throw new RuntimeException("Không tìm thấy RFID: " + rfid);
        };

        existing.setBlock(updated.getBlock());
        existing.setZone(updated.getZone());
        existing.setTime_in(updated.getTime_in());
        existing.setTime_out(updated.getTime_out());

        return PackageRepository.save(existing);  // Lúc này, object có ID gốc → Mongo sẽ cập nhật thay vì tạo mới
    }


    @Override
    public Optional<Package> getPackageByRfid(String rfid) {
        return Optional.empty();
    }
}
