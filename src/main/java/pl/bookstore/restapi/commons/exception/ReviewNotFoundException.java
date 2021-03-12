package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReviewNotFoundException extends ResponseStatusException {

    public ReviewNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Review with id " + id + " not found.");
    }
}