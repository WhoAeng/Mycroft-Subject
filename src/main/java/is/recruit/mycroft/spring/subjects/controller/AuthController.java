package is.recruit.mycroft.spring.subjects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import is.recruit.mycroft.spring.subjects.config.JwtTokenProvider;
import is.recruit.mycroft.spring.subjects.model.dto.ApiMessage;
import is.recruit.mycroft.spring.subjects.model.dto.AuthRequest;
import is.recruit.mycroft.spring.subjects.model.entity.User;
import is.recruit.mycroft.spring.subjects.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "토큰 정보 생성", description = "유저 정보를 통해 토큰 정보를 생성한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PostMapping("/login")
    public ResponseEntity<Mono<?>> loginAndGetToken(AuthRequest authRequest) throws Exception {

        User user = userService.getUser(authRequest.getUsername());
        if (user == null){
            return ResponseEntity.badRequest().body(Mono.just(new ApiMessage("사용자정보가 올바르지 않습니다")));
        }

        if (!passwordEncoder.matches(authRequest.getPassword(),user.getPassword())){
            return ResponseEntity.badRequest().body(Mono.just(new ApiMessage("비밀번호 불일치")));
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/login").toUriString());
        return ResponseEntity.created(uri).body(Mono.just(jwtTokenProvider.createToken(authRequest.getUsername(), user.getRoles().stream().collect(Collectors.toSet()))));
    }

}
