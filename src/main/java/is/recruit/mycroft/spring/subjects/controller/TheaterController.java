package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.model.entity.Theater;
import is.recruit.mycroft.spring.subjects.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TheaterController {
    private final TheaterRepository theaterRepository;

    @GetMapping("/movie/theaters")
    @Operation(summary = "영화별 상영관 조회" , description = "영화별로 상영관을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "not found")
    })
    public Flux<Theater> findAllByMovieIsNotNull(
    ){
        return Flux.fromIterable(theaterRepository.findAllByMovieIsNotNull());
    }


}
