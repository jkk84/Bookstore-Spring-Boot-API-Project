package pl.bookstore.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String obj, long id) {
        super(HttpStatus.NOT_FOUND, "Could not find " + obj + " " + id);
    }

    public NotFoundException(String obj, List<Long> id) {
        super(HttpStatus.NOT_FOUND, "Could not find " + obj + " " + id.toString());
    }
}
