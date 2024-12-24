package ru.almaz.authservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.almaz.authservice.exception.InvalidRefreshTokenException;
import ru.almaz.authservice.exception.InvalidTokenException;
import ru.almaz.authservice.exception.UserAlreadyExistException;
import ru.almaz.authservice.exception.UserUnauthenticatedException;

import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistException.class,
            UsernameNotFoundException.class,
    })
    public ProblemDetail handleUserAlreadyExistException(RuntimeException e) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
        log.error("handleConflictException: {}", response);
        return response;
    }

    @ExceptionHandler({
            UserUnauthenticatedException.class,
            InvalidTokenException.class,
            InvalidRefreshTokenException.class
    })
    public ProblemDetail handleUserUnauthenticatedException(RuntimeException e) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,e.getMessage());
        log.error("handleUnauthorizedException: {}", response);
        return response;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        log.error("handleForbiddenException: {}", response);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
       ProblemDetail response = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Error");
       response.setProperty(
               "errors",
               ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList()
       );
        return response;
    }




}
