package com.hauxy.projectwizard.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }
}
