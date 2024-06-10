package wojtki.onlineshopbackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user) {
        userService.registerUser(user);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(),"Successfully registered");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
