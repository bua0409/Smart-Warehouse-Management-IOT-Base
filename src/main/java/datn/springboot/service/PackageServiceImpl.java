package datn.springboot.service;

import datn.springboot.entity.Package;
import datn.springboot.entity.PackageStatus;
import datn.springboot.repo.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (existing != null) {
            existing.setPoId(pkg.getPoId());
            existing.setProductId(pkg.getProductId());
            existing.setBlock(pkg.getBlock());
            existing.setZone(pkg.getZone());
            existing.setTime_in(pkg.getTime_in());
            existing.setTime_out(pkg.getTime_out());

            // 🔒 Không cho phép override status nếu đã là ON_SHELF hoặc EXPORTED
            if (pkg.getStatus() != null &&
                    existing.getStatus() != PackageStatus.ON_SHELF &&
                    existing.getStatus() != PackageStatus.EXPORTED) {
                existing.setStatus(pkg.getStatus());
            }

            return PackageRepository.save(existing);
        }
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RFID không tồn tại: " + rfid);
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
