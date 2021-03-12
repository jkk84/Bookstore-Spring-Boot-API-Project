package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Book with id " + id + " not found.");
    }
}