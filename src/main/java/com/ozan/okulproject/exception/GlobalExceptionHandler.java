package com.ozan.okulproject.exception;


import com.ozan.okulproject.entity.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OkulProjectException.class)
    public ResponseEntity<ResponseWrapper> handleOkul(OkulProjectException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseWrapper.failure(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .findFirst().orElse("Validation error");
        return ResponseEntity.badRequest()
                .body(new ResponseWrapper(msg, null, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleOther(Exception ex) {
        // log ex
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper(ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseWrapper handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseWrapper(ex.getMessage(), null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseWrapper handleAuth(AuthenticationException ex) {
        return new ResponseWrapper(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
    }
}