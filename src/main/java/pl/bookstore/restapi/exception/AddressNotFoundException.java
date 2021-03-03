package pl.bookstore.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AddressNotFoundException extends ResponseStatusException {

    public AddressNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Address with id " + id + " not found.");
    }
}