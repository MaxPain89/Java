package com.mkalita.webserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputDataException extends RuntimeException{
    public InvalidInputDataException(String s) {
        super(s);
    }
}
