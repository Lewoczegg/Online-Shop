package wojtki.onlineshopbackend.dto;

import java.util.Date;

public record BecomeSellerRequest(String email, String IBAN, String address, Date dateOfBirth) {
}
