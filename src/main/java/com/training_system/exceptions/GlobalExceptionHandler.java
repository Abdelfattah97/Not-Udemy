package com.training_system.exceptions;


import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(getClass());
	
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
    @ExceptionHandler(DuplicateEnrollmentException.class)
    public ResponseEntity<String> handleDuplicateEnrollments(DuplicateEnrollmentException ex) {
    	return ResponseEntity.status(HttpStatus.CONFLICT).header("message", ex.getMessage()).body("Student is already enrolled in this course");
    }
    
    @ExceptionHandler(IllegalConfirmOperationException.class)
    public ResponseEntity<String> handleIllegalConfirmationOperation(DuplicateEnrollmentException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("An UnExcpected Error occurred! Contact Us at email@gmail.com");
    }
    
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInSuffientBalanceException(InsufficientBalanceException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).header("message", ex.getMessage()).body("Insufficient funds!");
    }
    
    @ExceptionHandler(NotRefundableException.class)
    public ResponseEntity<String> handleNotRefundable(NotRefundableException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).header("message", ex.getMessage()).body("This Payment is final and cannot be refunded!");
    }
    
    @ExceptionHandler(RefundFailureException.class)
    public ResponseEntity<String> handleRefundFailure(RefundFailureException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.BAD_GATEWAY).header("message", ex.getMessage()).body("Gateway error occured while processing this request!" + ex.getMessage());
    }
    
    
    @ExceptionHandler(UnSupportedOperationException.class)
    public ResponseEntity<String> handleUnSupportedOperation(UnSupportedOperationException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("This Operation is not supported!");
    }
    
    
    @ExceptionHandler(UserNotFountException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFountException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("user not found!");
    }
    
    @ExceptionHandler(NoRoleRegistrationException.class)
    public ResponseEntity<String> handleNoRoleRegistration(NoRoleRegistrationException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ex.getMessage()).body("Registering User with no role is not allowrd!");
    }
    
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<String> handleNoRoleRegistration(EntityAlreadyExistException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.CONFLICT).header("message", ex.getMessage()).body("Inserting A Duplicate record");
    }
    @ExceptionHandler(EntitiesMatchingException.class)
    public ResponseEntity<String> handleNoRoleRegistration(EntitiesMatchingException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ex.getMessage()).body(ex.getMessage());
    }
    
    
    
}
