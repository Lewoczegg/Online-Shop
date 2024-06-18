package wojtki.onlineshopbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import wojtki.onlineshopbackend.model.Order;
import wojtki.onlineshopbackend.model.OrderDetail;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.OrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void placeOrder_success() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        order.setOrderDetails(List.of(orderDetail));
        User user = new User();
        user.setId(1L);

        when(authentication.getName()).thenReturn("test@example.com");
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.placeOrder(order, authentication);

        verify(userService, times(1)).getUserByEmail("test@example.com");
        verify(orderRepository, times(1)).save(order);

        assertEquals(user, order.getUser());
        assertEquals(order, orderDetail.getOrder());
    }
}