package wojtki.onlineshopbackend.service;

import wojtki.onlineshopbackend.dto.BecomeSellerRequest;
import wojtki.onlineshopbackend.model.User;


public interface UserService {
    void registerUser(User user);
    User getUserByEmail(String email);
    void becomeSeller(BecomeSellerRequest becomeSellerRequest);
}
