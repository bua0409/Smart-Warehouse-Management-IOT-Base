package datn.springboot.repo;

import datn.springboot.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findOrderById(String id);

    String id(String id);
}
