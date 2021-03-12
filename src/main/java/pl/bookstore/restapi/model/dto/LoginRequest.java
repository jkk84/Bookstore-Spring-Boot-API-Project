package pl.bookstore.restapi.model.dto;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String login;
    private String password;
}
