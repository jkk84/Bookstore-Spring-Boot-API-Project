package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.model.dto.JwtResponse;
import pl.bookstore.restapi.model.dto.LoginRequest;
import pl.bookstore.restapi.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pl.bookstore.restapi.commons.Requests.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AUTH_LOGIN)
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginRequest, response));
    }

    @PostMapping(AUTH_LOGOUT)
    public void logout(HttpServletResponse response) {
        authService.logout(response);
    }

    @PostMapping(AUTH_REFRESH)
    public ResponseEntity<JwtResponse> refresh(HttpServletRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto,
                                            @AuthenticationPrincipal String login) {
        return ResponseEntity.ok(authService.register(userDto, login));
    }
}
