package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLoginOrPasswordException extends ResponseStatusException {

    public InvalidLoginOrPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid login or password.");
    }
}
