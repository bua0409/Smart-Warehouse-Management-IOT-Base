package datn.springboot.service;

import datn.springboot.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    void deleteOrder(String id);
}
