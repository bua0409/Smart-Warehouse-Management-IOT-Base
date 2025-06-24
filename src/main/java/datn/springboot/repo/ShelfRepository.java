package datn.springboot.repo;

import datn.springboot.entity.Shelf;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShelfRepository extends MongoRepository<Shelf, String> {

}
