package com.training_system.exceptions;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).header("message", ex.getMessage()).body("Data integrity violation");
    }
    
    @ExceptionHandler(UnknownStatusException.class)
    public ResponseEntity<String> handleUnknownStatus(UnknownStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Found Unknow Status");
    }
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIoException(IOException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
