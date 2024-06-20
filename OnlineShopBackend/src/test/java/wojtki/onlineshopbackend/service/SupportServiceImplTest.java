package wojtki.onlineshopbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wojtki.onlineshopbackend.model.SupportTicket;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.SupportTicketRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportServiceImplTest {
    @Mock
    private SupportTicketRepository supportTicketRepository;

    @InjectMocks
    private SupportServiceImpl supportService;

    @Test
    void getTickets_returnsTicketList() {
        List<SupportTicket> expectedTickets = List.of(new SupportTicket(), new SupportTicket());
        when(supportTicketRepository.findAll()).thenReturn(expectedTickets);

        List<SupportTicket> actualTickets = supportService.getTickets();

        assertEquals(expectedTickets, actualTickets);
        verify(supportTicketRepository, times(1)).findAll();
    }

    @Test
    void addTicket_success() {
        SupportTicket ticket = new SupportTicket();
        User user = new User();
        user.setId(1L);

        supportService.addTicket(ticket, user);

        verify(supportTicketRepository, times(1)).save(ticket);
        assertEquals(user, ticket.getUser());
    }
}