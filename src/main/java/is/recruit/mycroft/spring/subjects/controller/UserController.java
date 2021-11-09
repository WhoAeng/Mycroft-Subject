package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.model.entity.User;
import is.recruit.mycroft.spring.subjects.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "not found")
    })
    public ResponseEntity<Flux<User>> getUsers(){
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
}
