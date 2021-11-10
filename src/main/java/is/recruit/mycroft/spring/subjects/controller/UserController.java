package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.config.JwtTokenProvider;
import is.recruit.mycroft.spring.subjects.model.entity.User;
import is.recruit.mycroft.spring.subjects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<Flux<User>> getUsers() {
        return ResponseEntity.ok().body(Flux.fromIterable(userService.getUsers()));
    }

    /*
    @PostMapping("/user")
    public ResponseEntity<Mono<User>> saveUser(@RequestBody User user){
        User resp = userService.saveUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(Mono.just(userService.saveUser(user)));
    }
    */

    @PutMapping("/user/{id}/favorite-seat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true,dataType = "long",value = "사용자 id"),
            @ApiImplicitParam(name = "code",required = true,dataType = "string",value = "선호좌석 code",example = "GET /api/favorite-seats 선조회 후 원하는 code 값 입력")
    })
    public ResponseEntity<Mono<?>> addFavoriteSeatToUser(
            @PathVariable Long id,
            String code,
            HttpServletRequest request
    ) {

        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        User user = userService.getUser(jwtTokenProvider.getUsername(token));
        if (user.getId().equals(id)){
            userService.addFavoriteSeatToUser(user.getUsername(),code);
        }

        return ResponseEntity.ok().body(Mono.just(user));
    }
}
