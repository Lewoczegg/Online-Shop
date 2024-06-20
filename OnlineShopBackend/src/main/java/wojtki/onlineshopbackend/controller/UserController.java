package wojtki.onlineshopbackend.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.dto.BecomeSellerRequest;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user) {
        userService.registerUser(user);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(),"Successfully registered");
        logger.info("User registered successfully: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        logger.info("User logged in successfully: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/become-seller")
    public ResponseEntity<ApiResponse> becomeSeller(@Valid @RequestBody BecomeSellerRequest becomeSellerRequest) {
        userService.becomeSeller(becomeSellerRequest);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Successfully become seller");
        logger.info("User became seller successfully: {}", becomeSellerRequest.email());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
