package wojtki.onlineshopbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wojtki.onlineshopbackend.dto.EmailRequest;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Async
    public CompletableFuture<String> sendEmail(String to) {
        String url = "https://api.mailersend.com/v1/email";

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + System.getenv("MAIL_KEY"));
        headers.set("X-Requested-With", "XMLHttpRequest");

        // Create body
        EmailRequest.From from = new EmailRequest.From();
        from.setEmail("wojtki-shop@trial-pq3enl60z65l2vwr.mlsender.net");

        EmailRequest.To toRecipient = new EmailRequest.To();
        toRecipient.setEmail(to);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setFrom(from);
        emailRequest.setTo(Arrays.asList(toRecipient));
        emailRequest.setSubject("Order placed");
        emailRequest.setText("Your order has been placed");
        emailRequest.setHtml("Your order has been placed");

        // Create HttpEntity
        HttpEntity<EmailRequest> entity = new HttpEntity<>(emailRequest, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return CompletableFuture.completedFuture(response.getBody());
    }
}