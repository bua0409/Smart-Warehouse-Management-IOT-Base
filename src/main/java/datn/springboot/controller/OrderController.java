package datn.springboot.controller;

import datn.springboot.entity.Order;
import datn.springboot.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders (){
      return ResponseEntity.ok(orderService.getAllOrder());
    };

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder (@PathVariable String id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
