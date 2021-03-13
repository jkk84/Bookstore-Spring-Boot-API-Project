package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistException extends ResponseStatusException {

    public UserAlreadyExistException(String login) {
        super(HttpStatus.CONFLICT, "User '" + login + "' already exist.");
    }
}
