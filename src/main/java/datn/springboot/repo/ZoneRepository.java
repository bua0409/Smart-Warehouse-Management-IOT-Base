package datn.springboot.repo;

import datn.springboot.entity.Zone;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZoneRepository extends MongoRepository<Zone, String> {

}
