package is.recruit.mycroft.spring.subjects.repository;

import is.recruit.mycroft.spring.subjects.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByTheaterId(Long theaterId);
    Seat findByTheaterIdAndBookingIdAndSeatXAndSeatY(Long theaterId, int bookingId, int seatX, int seatY);
}
