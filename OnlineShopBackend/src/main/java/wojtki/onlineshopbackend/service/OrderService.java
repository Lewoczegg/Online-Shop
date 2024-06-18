package wojtki.onlineshopbackend.service;

import org.springframework.security.core.Authentication;
import wojtki.onlineshopbackend.model.Order;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order, Authentication authentication);
    List<Order> getMyOrders(Authentication authentication);
}
