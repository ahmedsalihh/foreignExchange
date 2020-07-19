package com.ahmedsalihh.foreignexchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCurrencyException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidCurrencyException(String message) {
        super(message);
    }
}
