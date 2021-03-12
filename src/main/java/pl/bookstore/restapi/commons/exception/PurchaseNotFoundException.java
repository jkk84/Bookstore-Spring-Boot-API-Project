package pl.bookstore.restapi.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class PurchaseNotFoundException extends ResponseStatusException {

    public PurchaseNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Purchase with id " + id + " not found.");
    }
}