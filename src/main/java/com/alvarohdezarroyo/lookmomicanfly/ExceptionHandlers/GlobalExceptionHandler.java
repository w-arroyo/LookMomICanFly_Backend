package com.alvarohdezarroyo.lookmomicanfly.ExceptionHandlers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.*;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import jakarta.mail.MessagingException;
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
    public ResponseEntity<Map<String,String>> emailAlreadyInUseHandler(EmailAlreadyInUseException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity<Map<String, Object>> emptyFieldsHandler(EmptyFieldsException ex){
        if(ex.getMessage()==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getEmptyFields()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,String>> entityNotFoundHandler(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(FraudulentRequestException.class)
    public ResponseEntity<Map<String,String>> fraudulentRequestHandler(FraudulentRequestException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<Map<String,String>> unauthorizedRequestExceptionHandler(UnauthorizedRequestException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> badCredentialsHandler(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String,String>> noDataFoundHandler(NoDataFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(SameValuesException.class)
    public ResponseEntity<Map<String,String>> sameValuesHandler(SameValuesException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> runTimeExceptionHandler(RuntimeException ex){

        Arrays.stream(ex.getStackTrace()).toList().forEach(
                System.out::println
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<Map<String,String>> unexpectedExceptionHandler(UnexpectedException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> illegalArgumentExceptionHandler(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<Map<String,String>> stripeSignatureVerificationExceptionHandler(SignatureVerificationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<Map<String,String>> stripeExceptionHandler(StripeException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(PaymentChargeUnsuccessfulException.class)
    public ResponseEntity<Map<String,String>> paymentChargeUnsuccessfulExceptionHandler(PaymentChargeUnsuccessfulException ex){
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(TrackingNumberAmountLimitReachedException.class)
    public ResponseEntity<Map<String,String>> trackingNumberAmountLimitReachedExceptionHandler(TrackingNumberAmountLimitReachedException ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(RejectedPostException.class)
    public ResponseEntity<Map<String,String>> rejectedPostExceptionHandler(RejectedPostException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(ProductAlreadyLikedException.class)
    public ResponseEntity<Map<String,String>> productAlreadyLikedExceptionHandler(ProductAlreadyLikedException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Map<String,String>> messagingExceptionHandler(MessagingException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",ex.getMessage()));
    }

}
