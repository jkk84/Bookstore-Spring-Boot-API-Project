package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.model.dto.JwtResponse;
import pl.bookstore.restapi.model.dto.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    JwtResponse login(LoginRequest loginRequest, HttpServletResponse response);

    UserDto register(UserDto userDto, String login);

    JwtResponse refresh(HttpServletRequest response);

    void logout(HttpServletResponse response);
}
