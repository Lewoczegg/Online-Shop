package wojtki.onlineshopbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wojtki.onlineshopbackend.exception.NotFoundException;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void getAllUsers_returnsUserList() {
        List<User> expectedUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = adminService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void banUser_userNotFound_throwsNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> adminService.banUser(1L));
    }

    @Test
    void banUser_success() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        adminService.banUser(1L);

        assertEquals(true, user.isBanned());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void unbanUser_userNotFound_throwsNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> adminService.unbanUser(1L));
    }

    @Test
    void unbanUser_success() {
        User user = new User();
        user.setId(1L);
        user.setBanned(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        adminService.unbanUser(1L);

        assertEquals(false, user.isBanned());
        verify(userRepository, times(1)).save(user);
    }
}