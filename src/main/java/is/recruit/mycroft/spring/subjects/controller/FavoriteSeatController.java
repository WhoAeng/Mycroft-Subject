package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.config.JwtTokenProvider;
import is.recruit.mycroft.spring.subjects.model.dto.ApiMessage;
import is.recruit.mycroft.spring.subjects.model.entity.FavoriteSeat;
import is.recruit.mycroft.spring.subjects.model.entity.Seat;
import is.recruit.mycroft.spring.subjects.model.entity.Theater;
import is.recruit.mycroft.spring.subjects.model.entity.User;
import is.recruit.mycroft.spring.subjects.repository.FavoriteSeatRepository;
import is.recruit.mycroft.spring.subjects.repository.SeatRepository;
import is.recruit.mycroft.spring.subjects.repository.TheaterRepository;
import is.recruit.mycroft.spring.subjects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FavoriteSeatController {
    private final SeatRepository seatRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final FavoriteSeatRepository favoriteSeatRepository;
    private final TheaterRepository theaterRepository;

    @GetMapping("/theater/{id}/favorite-seats")
    @Operation(summary = "상영관의 사용자 선호좌석 조회", description = "상영관id를 사용하여 사용자의 선호좌석 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "not found")
    })
    public ResponseEntity<Flux<?>> findAll(
            @PathVariable
            @Parameter(description = "상영관id", required = true, example = "숫자") int id,
            @RequestHeader("Authorization") String authorization
    ) {
        String token = authorization.substring(7);
        User user = userService.getUser(jwtTokenProvider.getUsername(token));
        FavoriteSeat favoriteSeat = user.getFavoriteSeat();
        if (favoriteSeat == null){
            return ResponseEntity.notFound().build();
        }
        String code = favoriteSeat.getCode();
        System.out.println("code: " + code);

        String yCode = code.substring(0, 1);
        String xCode = code.substring(1);
        System.out.println("yCode: " + yCode);
        System.out.println("xCode: " + xCode);

        Theater theater = theaterRepository.getById((long) id);
        if (theater == null) {
            return ResponseEntity.badRequest().body(Flux.just(new ApiMessage("상영관을 찾을 수 없습니다")));
        }

        int seatXCount = theater.getSeatXCount();
        int seatYCount = theater.getSeatYCount();

        int seatXStartIdx = 0;
        int seatXEndIdx = 0;
        int seatYStartIdx = 0;
        int seatYEndIdx = 0;


        switch (yCode) {
            case "F":
                seatYStartIdx = 1;
                seatYEndIdx = seatYCount / 3;
                break;
            case "M":
                seatYStartIdx = seatYCount / 3 + 1;
                seatYEndIdx = seatYCount - seatYCount / 3;
                break;
            case "R":
                seatYStartIdx = seatYCount - seatYCount / 3 + 1;
                seatYEndIdx = seatYCount;
                break;
        }

        switch (xCode) {
            case "L":
                seatXStartIdx = 1;
                seatXEndIdx = seatXCount / 3;
                break;
            case "M":
                seatXStartIdx = seatXCount / 3 + 1;
                seatXEndIdx = seatXCount - seatXCount / 3;
                break;
            case "R":
                seatXStartIdx = seatXCount - seatXCount / 3 + 1;
                seatXEndIdx = seatXCount;
                break;
        }

        List<Seat> seats = new ArrayList<>();

        for (int i = seatYStartIdx; i <= seatYEndIdx; i++) {
            for (int j = seatXStartIdx; j <= seatXEndIdx; j++) {
                Seat seat = seatRepository.findByTheaterIdAndBookingIdAndSeatXAndSeatY(id, 0, j, i);
                if (seat!=null) seats.add(seat);
            }
        }


        return ResponseEntity.ok(Flux.fromIterable(seats));
    }

}
