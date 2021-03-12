package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenNotFoundException extends ResponseStatusException {

    public RefreshTokenNotFoundException() {
        super(HttpStatus.UNAUTHORIZED, "Refresh token not found.");
    }
}
