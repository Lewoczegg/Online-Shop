package wojtki.onlineshopbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wojtki.onlineshopbackend.dto.BecomeSellerRequest;
import wojtki.onlineshopbackend.exception.DuplicateException;
import wojtki.onlineshopbackend.exception.NotFoundException;
import wojtki.onlineshopbackend.model.Authority;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.AuthorityRepository;
import wojtki.onlineshopbackend.repository.UserRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void becomeSeller_userNotFound_throwsNotFoundException() {
        BecomeSellerRequest request = new BecomeSellerRequest("test@example.com", "IBAN123", "Test Address", new Date());
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.becomeSeller(request));
    }

    @Test
    void becomeSeller_userAlreadySeller_throwsDuplicateException() {
        // Arrange
        User user = new User();
        Authority authority = new Authority();
        authority.setName("ROLE_SELLER");
        user.setAuthorities(Set.of(authority));
        BecomeSellerRequest request = new BecomeSellerRequest("test@example.com", "IBAN123", "Test Address", new Date());

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(DuplicateException.class ,() -> userService.becomeSeller(request));
    }

    @Test
    void becomeSeller_success() {
        // Arrange
        User user = new User();
        user.setAuthorities(new HashSet<>());
        BecomeSellerRequest request = new BecomeSellerRequest("test@example.com", "IBAN123", "Test Address", new Date());

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));

        // Act
        userService.becomeSeller(request);

        // Assert
        verify(authorityRepository, times(1)).save(any(Authority.class));
        verify(userRepository, times(1)).save(user);
        assertEquals("IBAN123", user.getIBAN());
        assertEquals("Test Address", user.getAddress());
        assertTrue(user.getAuthorities().stream().anyMatch(auth -> "ROLE_SELLER".equals(auth.getName())));
    }
}