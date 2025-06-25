package datn.springboot.repo;

import datn.springboot.entity.Package;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PackageRepository extends MongoRepository<Package,String> {
    Package findByRfid(String rfid);

}
