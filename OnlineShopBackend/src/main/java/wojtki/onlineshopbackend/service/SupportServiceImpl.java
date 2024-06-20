package wojtki.onlineshopbackend.service;

import org.springframework.stereotype.Service;
import wojtki.onlineshopbackend.model.SupportTicket;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.SupportTicketRepository;

import java.util.List;

@Service
public class SupportServiceImpl implements SupportService {
    private final SupportTicketRepository supportTicketRepository;

    public SupportServiceImpl(SupportTicketRepository supportTicketRepository) {
        this.supportTicketRepository = supportTicketRepository;
    }

    @Override
    public List<SupportTicket> getTickets() {
        return supportTicketRepository.findAll();
    }

    @Override
    public void addTicket(SupportTicket ticket, User user) {
        ticket.setUser(user);
        supportTicketRepository.save(ticket);
    }
}
