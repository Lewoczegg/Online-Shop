package wojtki.onlineshopbackend.service;

import org.springframework.security.core.Authentication;
import wojtki.onlineshopbackend.model.SupportTicket;
import wojtki.onlineshopbackend.model.User;

import java.util.List;

public interface SupportService {
    List<SupportTicket> getTickets();
    void addTicket(SupportTicket ticket, User user);
}
