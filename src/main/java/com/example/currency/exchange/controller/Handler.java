package com.example.currency.exchange.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleAllExceptions(RuntimeException exception){
        return ResponseEntity.badRequest().body("something went wrong\n" + exception.getMessage());
    }
}
