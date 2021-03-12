package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerAlreadyExistException extends ResponseStatusException {

    public CustomerAlreadyExistException(String login) {
        super(HttpStatus.CONFLICT, "Customer '" + login + "' already exist.");
    }
}
