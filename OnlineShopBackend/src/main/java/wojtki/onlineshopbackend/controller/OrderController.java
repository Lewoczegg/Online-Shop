package wojtki.onlineshopbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.model.Order;
import wojtki.onlineshopbackend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody Order order, Authentication authentication) {
        orderService.placeOrder(order, authentication);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), "New order placed");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        List<Order> orders = orderService.getMyOrders(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
