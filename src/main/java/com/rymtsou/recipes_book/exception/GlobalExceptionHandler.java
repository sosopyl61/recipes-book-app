package com.rymtsou.recipes_book.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> allExceptionsHandler(Exception exception) {
        return buildErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedExceptionHandler(AccessDeniedException exception) {
        return buildErrorResponse(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<ErrorResponse> jwtExceptionHandler(JwtException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundExceptionHandler(UsernameNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsExceptionHandler(BadCredentialsException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(ExpiredJwtException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
