package wojtki.onlineshopbackend.service;

import wojtki.onlineshopbackend.model.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    void banUser(Long userId);
    void unbanUser(Long userId);
}
