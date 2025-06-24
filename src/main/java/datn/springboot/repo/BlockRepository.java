package datn.springboot.repo;

import datn.springboot.entity.Block;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRepository extends MongoRepository<Block, String> {

}
