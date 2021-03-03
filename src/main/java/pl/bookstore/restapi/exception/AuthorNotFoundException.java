package pl.bookstore.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorNotFoundException extends ResponseStatusException {

    public AuthorNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Author with id " + id + " not found.");
    }
}