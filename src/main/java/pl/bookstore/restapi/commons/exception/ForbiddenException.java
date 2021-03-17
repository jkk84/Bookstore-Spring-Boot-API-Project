package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ForbiddenException extends ResponseStatusException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "No Access.");
    }
}