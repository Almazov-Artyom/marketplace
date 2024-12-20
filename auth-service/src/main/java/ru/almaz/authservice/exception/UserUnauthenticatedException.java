package ru.almaz.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserUnauthenticatedException extends RuntimeException {
    public UserUnauthenticatedException(String message) {super(message);}

}
