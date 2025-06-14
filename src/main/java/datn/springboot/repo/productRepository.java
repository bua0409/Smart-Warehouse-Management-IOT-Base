package datn.springboot.repo;

import datn.springboot.entity.product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface productRepository extends MongoRepository<product,String> {

}
