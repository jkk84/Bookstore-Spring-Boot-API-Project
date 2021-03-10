package pl.bookstore.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotFoundException extends ResponseStatusException {

    public OrderNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Order with id " + id + " not found.");
    }
}