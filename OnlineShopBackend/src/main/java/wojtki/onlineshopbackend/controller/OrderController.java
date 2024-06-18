package wojtki.onlineshopbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.model.Order;
import wojtki.onlineshopbackend.service.OrderService;

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
}
