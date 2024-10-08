package com.etiqa.assessment.exception;

import com.etiqa.assessment.exception.customException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ExceptionDetails>> handleResourceNotFoundException(ResourceNotFoundException exception, ServerWebExchange exchange) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                exchange.getRequest().getPath().value(),
                "RESOURCE_NO_FOUND"
        );
        return Mono.just(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionDetails));
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public Mono<ResponseEntity<ExceptionDetails>> handleEmailAlreadyExistException(EmailAlreadyExistException exception, ServerWebExchange exchange) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                exchange.getRequest().getPath().value(),
                "RESOURCE_ALREADY_EXISTS"
        );
        return Mono.just(ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionDetails));
    }

    @ExceptionHandler(NoRequestParamException.class)
    public Mono<ResponseEntity<ExceptionDetails>> handleNoRequestParamException(NoRequestParamException exception, ServerWebExchange exchange){
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                exchange.getRequest().getPath().value(),
                "BAD_REQUEST"
        );
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDetails));
    }

    @ExceptionHandler(NoRequestBodyException.class)
    public Mono<ResponseEntity<ExceptionDetails>> handleNoRequestBodyException(NoRequestBodyException exception, ServerWebExchange exchange){
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                exchange.getRequest().getPath().value(),
                "BAD_REQUEST"
        );
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDetails));
    }

    @ExceptionHandler(InsufficientProductQuantityException.class)
    public Mono<ResponseEntity<ExceptionDetails>> handleInsufficientProductQuantityException(InsufficientProductQuantityException exception, ServerWebExchange exchange){
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                exchange.getRequest().getPath().value(),
                "BAD_REQUEST"
        );
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDetails));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ExceptionDetails>> handleResourceNotFoundException(Exception exception, ServerWebExchange exchange) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                exchange.getRequest().getPath().value(),
                "INTERNAL SERVER ERROR"
        );
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDetails));
    }

    // Handle validation exceptions for WebFlux (WebExchangeBindException)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleValidationExceptions(WebExchangeBindException ex, ServerWebExchange exchange) {
        // Extract validation errors and build a custom error response
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Return the validation errors as the response body
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors));
    }
}
