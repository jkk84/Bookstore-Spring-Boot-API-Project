package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.CustomerDto;
import pl.bookstore.restapi.model.dto.JwtResponse;
import pl.bookstore.restapi.model.dto.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    JwtResponse login(LoginRequest loginRequest, HttpServletResponse response);

    CustomerDto register(CustomerDto customerDto);

    JwtResponse refresh(HttpServletRequest response);

    void logout(HttpServletResponse response);
}
