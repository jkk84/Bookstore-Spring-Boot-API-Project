package pl.bookstore.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerNotFoundException extends ResponseStatusException {

    public CustomerNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found.");
    }
}