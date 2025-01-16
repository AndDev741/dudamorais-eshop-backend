package com.dudamorais.eshop.exceptions;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Map<String, String>> userNotFoundExceptionHandler(UserNotFound ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    } 

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Map<String, String>> jwtVerificationExceptionHandler(JWTVerificationException ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<Map<String, String>> productNotFoundExceptionHandler(ProductNotFound ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(SizeAndQuantityNotFound.class)
    public ResponseEntity<Map<String, String>> sizeAndQuantityNotFoundExceptionHandler(SizeAndQuantityNotFound ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(TypeNotFound.class)
    public ResponseEntity<Map<String, String>> typeNotFoundExceptionHandler(TypeNotFound ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
