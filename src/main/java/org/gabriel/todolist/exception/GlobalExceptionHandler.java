package org.gabriel.todolist.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestErrorResponse> handlerBadCredentialsException(BadCredentialsException e) {

        return ResponseEntity.status(401).body(
                new RestErrorResponse.RestErrorResponseBuilder()
                        .setError(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .setMessage("Invalid email or password")
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestErrorResponse> handlerConstraintViolationException(ConstraintViolationException e) {

        return ResponseEntity.status(409).body(
                new RestErrorResponse.RestErrorResponseBuilder()
                        .setError(HttpStatus.CONFLICT.getReasonPhrase())
                        .setMessage("email already exists")
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handlerAccessDeniedException(AccessDeniedException e) {

        return ResponseEntity.status(403).body(
                new RestErrorResponse.RestErrorResponseBuilder()
                        .setError(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .setMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handlerTaskNotFoundException(ResourceNotFoundException e) {

        return ResponseEntity.status(404).body(
                new RestErrorResponse.RestErrorResponseBuilder()
                        .setError(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .setMessage(e.getMessage())
                        .build());
    }
}
