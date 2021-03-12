package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerNotFoundException extends ResponseStatusException {

    public CustomerNotFoundException(String login) {
        super(HttpStatus.NOT_FOUND, "Customer '" + login + "' not found.");
    }
}