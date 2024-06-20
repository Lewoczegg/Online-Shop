package wojtki.onlineshopbackend.service;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<String> sendEmail(String to);
}
