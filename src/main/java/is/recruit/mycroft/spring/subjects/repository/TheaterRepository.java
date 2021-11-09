package is.recruit.mycroft.spring.subjects.repository;

import is.recruit.mycroft.spring.subjects.model.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Long> {
    List<Theater> findAllByMovieIsNotNull();
}
