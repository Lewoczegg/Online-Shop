package wojtki.onlineshopbackend.service;

import org.springframework.security.core.Authentication;
import wojtki.onlineshopbackend.model.Order;

public interface OrderService {
    void placeOrder(Order order, Authentication authentication);
}
