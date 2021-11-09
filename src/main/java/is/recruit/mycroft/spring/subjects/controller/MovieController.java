package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.model.entity.Movie;
import is.recruit.mycroft.spring.subjects.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping("/movie/{id}")
    @Operation(summary = "영화 조회" , description = "영화id를 사용하여 영화 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "not found")
    })
    public Mono<Optional<Movie>> findById(
            @Parameter(description = "영화id", required = true, example = "숫자")
            @PathVariable Long id
    ){
        return Mono.just( movieRepository.findById(id));
    }

    @GetMapping("/movies")
    public Flux<Movie> findAll(){
        return Flux.fromIterable(movieRepository.findAll());
    }


}
