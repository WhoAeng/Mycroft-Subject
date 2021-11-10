package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.model.entity.Seat;
import is.recruit.mycroft.spring.subjects.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SeatController {
    private final SeatRepository seatRepository;

    @GetMapping("/theater/{id}/seats")
    @Operation(summary = "상영관 좌석정보 조회" , description = "상영관id를 사용하여 좌석 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public Flux<Seat> findAllByTheaterId(
            @PathVariable
            @Parameter(description = "상영관id", required = true, example = "숫자") Long id
    ){
        return Flux.fromIterable( seatRepository.findAllByTheaterId(id));
    }

}
