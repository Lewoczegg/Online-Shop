package wojtki.onlineshopbackend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wojtki.onlineshopbackend.exception.DuplicateException;
import wojtki.onlineshopbackend.exception.NotFoundException;
import wojtki.onlineshopbackend.model.Authority;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.AuthorityRepository;
import wojtki.onlineshopbackend.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateException("User with this email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setName("ROLE_USER");
        authorityRepository.save(authority);
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            return user.get();
        }
    }
}
