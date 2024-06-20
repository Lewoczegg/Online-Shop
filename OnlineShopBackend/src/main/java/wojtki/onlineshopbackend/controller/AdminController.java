package wojtki.onlineshopbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PatchMapping("/ban")
    public ResponseEntity<ApiResponse> banUser(@RequestParam Long userId) {
        adminService.banUser(userId);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "User banned");
        logger.info("User with ID: {} banned successfully", userId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PatchMapping("/unban")
    public ResponseEntity<ApiResponse> unbanUser(@RequestParam Long userId) {
        adminService.unbanUser(userId);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "User unbanned");
        logger.info("User with ID: {} unbanned successfully", userId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
