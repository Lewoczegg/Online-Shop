package wojtki.onlineshopbackend.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wojtki.onlineshopbackend.dto.ApiResponse;
import wojtki.onlineshopbackend.model.SupportTicket;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.service.SupportService;
import wojtki.onlineshopbackend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/support")
public class SupportController {
    private static final Logger logger = LoggerFactory.getLogger(SupportController.class);
    private final SupportService supportService;
    private final UserService userService;

    public SupportController(SupportService supportService, UserService userService) {
        this.supportService = supportService;
        this.userService = userService;
    }

    @GetMapping("/get-tickets")
    public ResponseEntity<List<SupportTicket>> getTickets() {
        List<SupportTicket> tickets = supportService.getTickets();
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTicket(
            @Valid @RequestBody SupportTicket ticket,
            Authentication authentication
    ) {
        User user = userService.getUserByEmail(authentication.getName());
        supportService.addTicket(ticket, user);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Ticket added");
        logger.info("Support ticket added successfully for user: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
