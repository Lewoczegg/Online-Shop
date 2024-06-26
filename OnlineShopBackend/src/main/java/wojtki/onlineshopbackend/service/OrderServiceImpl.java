package wojtki.onlineshopbackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import wojtki.onlineshopbackend.model.Order;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.OrderRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final EmailService emailService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void placeOrder(Order order, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());

        order.setUser(user);

        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));

        emailService.sendEmail(user.getEmail());

        orderRepository.save(order);
    }

    @Override
    public List<Order> getMyOrders(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());

        return orderRepository.getOrderByUserId(user.getId());
    }
}
