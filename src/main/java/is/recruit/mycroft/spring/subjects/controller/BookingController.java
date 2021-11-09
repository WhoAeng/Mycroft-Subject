package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.config.JwtTokenProvider;
import is.recruit.mycroft.spring.subjects.model.dto.ApiMessage;
import is.recruit.mycroft.spring.subjects.model.dto.BookingRequest;
import is.recruit.mycroft.spring.subjects.model.entity.*;
import is.recruit.mycroft.spring.subjects.repository.*;
import is.recruit.mycroft.spring.subjects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookingController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    @PostMapping("/booking")
    @Operation(summary = "예매 정보 생성", description = "예매 정보를 생성한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "409", description = "conflict"),
            @ApiResponse(responseCode = "422", description = "unprocessable entity")
    })
    public ResponseEntity<Mono<?>> createBooking(
            @Parameter(description = "영화id", required = true, example = "숫자")
            @RequestBody(required = true) BookingRequest bookingRequest,
            @RequestHeader("Authorization") String authorization
    ) {
        String token = authorization.substring(7);
        User user = userService.getUser(jwtTokenProvider.getUsername(token));
        Theater theater = theaterRepository.findById((long) bookingRequest.getTheaterId()).get();
        Movie movie = movieRepository.findById(theater.getMovie().getMovieId()).get();
        Seat seat = seatRepository.findById((long) bookingRequest.getSeatId()).get();

        if (seat.getBookingId() != 0) return ResponseEntity.status(HttpStatus.CONFLICT).body(Mono.just(new ApiMessage("이미 예매된 좌석입니다")));

        int finalPrice = movie.getPrice() + seat.getSurcharge();
        if(bookingRequest.getPrice() != finalPrice){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Mono.just(new ApiMessage("가격 오류. 좌석가격: "+finalPrice + " 입력가격: " + bookingRequest.getPrice())));
        }

        Booking booking = bookingRepository.save(Booking.builder().user(user).movie(movie).theater(theater).seat(seat).price(finalPrice).build());
        seat.setBookingId(booking.getBookingId().intValue());
        seatRepository.save(seat);
        ticketRepository.save(Ticket.builder().booking(booking).user(user).build());

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/booking").toUriString());

        return ResponseEntity.created(uri).body(Mono.just(booking));
    }


}
