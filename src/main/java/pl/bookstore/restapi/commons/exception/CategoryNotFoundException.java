package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotFoundException extends ResponseStatusException {

    public CategoryNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Category with id " + id + " not found.");
    }
}