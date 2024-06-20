package wojtki.onlineshopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wojtki.onlineshopbackend.model.SupportTicket;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}
