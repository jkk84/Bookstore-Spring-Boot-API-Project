package pl.bookstore.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String obj, Long id) {
        super(HttpStatus.NOT_FOUND, "Could not find " + obj + " " + id);
    }
}
