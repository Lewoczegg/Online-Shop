package wojtki.onlineshopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wojtki.onlineshopbackend.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
