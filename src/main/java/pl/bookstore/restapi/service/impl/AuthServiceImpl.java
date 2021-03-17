package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;
import pl.bookstore.restapi.commons.enums.TokenType;
import pl.bookstore.restapi.commons.exception.ForbiddenException;
import pl.bookstore.restapi.commons.exception.InvalidLoginOrPasswordException;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.jwt.JwtUtil;
import pl.bookstore.restapi.mapper.UserMapper;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.model.dto.JwtResponse;
import pl.bookstore.restapi.model.dto.LoginRequest;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.repository.UserRepository;
import pl.bookstore.restapi.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static pl.bookstore.restapi.commons.Requests.AUTH_REFRESH;
import static pl.bookstore.restapi.commons.enums.RoleType.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";
    public static final String LOGOUT_REFRESH_TOKEN_COOKIE = "logoutRefreshToken";

    @Value("${jwt.access.token.expiration.seconds}")
    private long accessTokenExpirationSeconds;
    @Value("${jwt.refresh.token.expiration.seconds}")
    private long refreshTokenExpirationSeconds;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse login(LoginRequest loginRequest, HttpServletResponse httpResponse) {
        String accessToken;
        UserEntity userEntity = userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getLogin()));
        UserDto userDto = userMapper.toDto(userEntity);
        if (passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            accessToken = jwtUtil.getJwtToken(loginRequest.getLogin(), userDto.getRoles(), TokenType.ACCESS, accessTokenExpirationSeconds);
            String refreshToken = jwtUtil.getJwtToken(loginRequest.getLogin(), userDto.getRoles(), TokenType.REFRESH,
                    refreshTokenExpirationSeconds);
            String logoutRefreshToken = jwtUtil.getJwtToken(loginRequest.getLogin(), userDto.getRoles(), TokenType.LOGOUT_REFRESH,
                    refreshTokenExpirationSeconds);
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, getRefreshTokenCookie(refreshToken).toString());
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, getLogoutRefreshTokenCookie(logoutRefreshToken).toString());
        } else {
            throw new InvalidLoginOrPasswordException();
        }
        return new JwtResponse(accessToken);
    }

    @Override
    public void logout(HttpServletResponse response) {
        ResponseCookie emptyRefreshCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, EMPTY)
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, emptyRefreshCookie.toString());
    }

    @Override
    public UserDto register(UserDto userDto, String login) {
        if((!userDto.getLogin().equals("anonymousUser")) && login.equals("anonymousUser") && userDto.getRoles().equals(List.of(ROLE_CUSTOMER))) {
            return saveUserDto(userDto);
        } else if(login.equals("anonymousUser") || userDto.getRoles().equals(List.of(ROLE_CUSTOMER))) {
            throw new ForbiddenException();
        } else {
            UserDto loggedUser = userMapper.toDto(userRepository.findByLogin(login)
                    .orElseThrow(() -> new UserNotFoundException(login)));
            if ((userDto.getRoles().contains(ROLE_ADMIN) && loggedUser.getRoles().contains(ROLE_ADMIN)) ||
                    (!userDto.getRoles().contains(ROLE_ADMIN) && loggedUser.getRoles().contains(ROLE_MANAGER))) {
                return saveUserDto(userDto);
            } else throw new ForbiddenException();
        }
    }

    @Override
    public JwtResponse refresh(HttpServletRequest request) {
        Cookie refreshToken = WebUtils.getCookie(request, REFRESH_TOKEN_COOKIE);
        Cookie logoutRefreshToken = WebUtils.getCookie(request, LOGOUT_REFRESH_TOKEN_COOKIE);
        String accessToken = jwtUtil.getAccessTokenFromRefreshTokens(refreshToken, logoutRefreshToken);
        return new JwtResponse(accessToken);
    }

    private ResponseCookie getRefreshTokenCookie(String token) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE, token)
                .httpOnly(true)
                .sameSite("lax")
                .path(AUTH_REFRESH)
                .maxAge(refreshTokenExpirationSeconds)
                .secure(false)
                .build();
    }

    private ResponseCookie getLogoutRefreshTokenCookie(String token) {
        return ResponseCookie.from(LOGOUT_REFRESH_TOKEN_COOKIE, token)
                .sameSite("lax")
                .path(AUTH_REFRESH)
                .maxAge(refreshTokenExpirationSeconds)
                .secure(false)
                .build();
    }

    private UserDto saveUserDto(UserDto userDto) {
        return Optional.of(userDto)
                .filter(dto -> (!userRepository.existsByLogin(dto.getLogin()) && (!userRepository.existsByEmail(dto.getEmail()))))
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Login or/and email already used."));
    }
}
