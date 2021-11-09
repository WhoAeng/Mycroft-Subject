package is.recruit.mycroft.spring.subjects.repository;

import is.recruit.mycroft.spring.subjects.model.entity.FavoriteSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteSeatRepository extends JpaRepository<FavoriteSeat, Long> {
    FavoriteSeat findByCode(String code);
}
