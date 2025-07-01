package datn.springboot.repo;

import datn.springboot.entity.EspConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EspConfigRepository extends MongoRepository<EspConfig, String> {
    Optional<EspConfig> findByEspId(String espId);
}
