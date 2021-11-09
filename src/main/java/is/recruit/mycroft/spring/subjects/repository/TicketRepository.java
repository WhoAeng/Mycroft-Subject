package is.recruit.mycroft.spring.subjects.repository;

import is.recruit.mycroft.spring.subjects.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
