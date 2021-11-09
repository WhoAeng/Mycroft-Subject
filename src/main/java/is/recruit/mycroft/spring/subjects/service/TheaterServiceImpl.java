package is.recruit.mycroft.spring.subjects.service;

import is.recruit.mycroft.spring.subjects.model.entity.Movie;
import is.recruit.mycroft.spring.subjects.model.entity.Theater;
import is.recruit.mycroft.spring.subjects.repository.MovieRepository;
import is.recruit.mycroft.spring.subjects.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TheaterServiceImpl implements TheaterService{

    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;

    @Override
    public void addMovieToTheater(Long theaterId, Long movieId) {
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(IllegalArgumentException::new);
        Movie movie = movieRepository.findById(movieId).orElseThrow(IllegalArgumentException::new);
        theater.setMovie(movie);
        theaterRepository.save(theater);
    }
}
