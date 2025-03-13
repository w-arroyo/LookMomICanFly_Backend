package com.alvarohdezarroyo.lookmomicanfly.ExceptionHandlers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.UnexpectedException;
import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> emailAlreadyInUseHandler(EmailAlreadyInUseException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity<Map<String, Object>> emptyFieldsHandler(EmptyFieldsException ex){
        if(ex.getMessage()==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors",ex.getEmptyFields()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors",ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundHandler(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FraudulentRequestException.class)
    public ResponseEntity<String> fraudulentRequestHandler(FraudulentRequestException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<String> unauthorizedRequestExceptionHandler(UnauthorizedRequestException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsHandler(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<String> noDataFoundHandler(NoDataFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SameValuesException.class)
    public ResponseEntity<String> sameValuesHandler(SameValuesException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runTimeExceptionHandler(RuntimeException ex){
        /*
        Arrays.stream(ex.getStackTrace()).toList().forEach(
                System.out::println
        );
        */
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<String> unexpectedExceptionHandler(UnexpectedException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
