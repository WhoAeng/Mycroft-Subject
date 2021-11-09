package is.recruit.mycroft.spring.subjects.repository;

import is.recruit.mycroft.spring.subjects.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
