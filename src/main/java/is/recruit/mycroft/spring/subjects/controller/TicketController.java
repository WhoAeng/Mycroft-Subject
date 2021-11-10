package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.config.JwtTokenProvider;
import is.recruit.mycroft.spring.subjects.model.dto.ApiMessage;
import is.recruit.mycroft.spring.subjects.model.entity.Booking;
import is.recruit.mycroft.spring.subjects.model.entity.Ticket;
import is.recruit.mycroft.spring.subjects.model.entity.User;
import is.recruit.mycroft.spring.subjects.repository.BookingRepository;
import is.recruit.mycroft.spring.subjects.repository.TicketRepository;
import is.recruit.mycroft.spring.subjects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TicketController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    @GetMapping("/user/{id}/ticket")
    @Operation(summary = "티켓 정보 확인", description = "티켓id를 사용하여 예매 정보를 확인한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Flux<?>> getAllByUser(
            @Parameter(description = "사용자id", required = true, example = "숫자")
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        User user = userService.getUser(jwtTokenProvider.getUsername(token));
        List<Long> idList = new ArrayList<>();
        if (!user.getId().equals(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Flux.just("본인의 티켓만 조회할 수 있습니다"));
        idList.add(user.getId());
        List<Ticket> ticketList = ticketRepository.findAllById(idList);
        if (ticketList.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(Flux.fromIterable(ticketList));
    }

    @GetMapping("/ticket/{id}/booking")
    @Operation(summary = "예매 정보 확인", description = "티켓id를 사용하여 예매 정보를 확인한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Mono<?>> getBookingByTicketId(
            @Parameter(description = "티켓id", required = true, example = "숫자")
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        User user = userService.getUser(jwtTokenProvider.getUsername(token));

        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            return ResponseEntity.badRequest().body(Mono.just(new ApiMessage("티켓 정보가 없습니다")));
        }
        if (!ticket.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(Mono.just(new ApiMessage("사용자 소유의 티켓이 아닙니다")));
        }
        Booking booking = bookingRepository.findById(ticket.getBooking().getBookingId()).orElse(null);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(Mono.just(booking));
    }


}
