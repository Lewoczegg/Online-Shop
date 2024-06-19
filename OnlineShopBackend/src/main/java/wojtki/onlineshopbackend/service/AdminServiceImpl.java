package wojtki.onlineshopbackend.service;

import org.springframework.stereotype.Service;
import wojtki.onlineshopbackend.exception.NotFoundException;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void banUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        user.get().setBanned(true);
        userRepository.save(user.get());
    }

    @Override
    public void unbanUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        user.get().setBanned(false);
        userRepository.save(user.get());
    }
}
