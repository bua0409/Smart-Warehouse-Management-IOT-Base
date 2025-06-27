package datn.springboot.service;

import datn.springboot.entity.Order;
import datn.springboot.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
