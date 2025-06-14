package datn.springboot.repo;

import datn.springboot.entity.Package;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PackageRepository extends MongoRepository<Package,String> {

}
