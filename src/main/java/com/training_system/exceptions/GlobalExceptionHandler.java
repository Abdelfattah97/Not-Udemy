package com.training_system.exceptions;


import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("message", ex.getMessage()).body("Found Unknow Status");
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
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("This Operation is Not Supported!");
    }
    
    
    @ExceptionHandler(UserNotFountException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFountException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("User Not Found!");
    }

    @ExceptionHandler(DuplicateLessonException.class)
    public ResponseEntity<String> duplicateLesson(DuplicateLessonException ex){
        logger.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).header("message", ex.getMessage()).body("Duplicated Lesson");
    }

    @ExceptionHandler(DuplicateCourseException.class)
    public ResponseEntity<String> duplicateLesson(DuplicateCourseException ex){
        logger.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).header("message", ex.getMessage()).body("Duplicated Course");
    }
    
    @ExceptionHandler(NoRoleRegistrationException.class)
    public ResponseEntity<String> handleNoRoleRegistration(NoRoleRegistrationException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ex.getMessage()).body("Registering User With No Role Is Not Allowrd!");
    }
    
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<String> handleNoRoleRegistration(EntityAlreadyExistException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.CONFLICT).header("message", ex.getMessage()).body("Inserting A Duplicate Record");
    }
    @ExceptionHandler(EntitiesMatchingException.class)
    public ResponseEntity<String> handleNoRoleRegistration(EntitiesMatchingException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ex.getMessage()).body(ex.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleNoRoleRegistration(AccessDeniedException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).header("message", ex.getMessage()).body(ex.getMessage());
    }
    @ExceptionHandler(ResourceUploadException.class)
    public ResponseEntity<String> handleFileUploadException(ResourceUploadException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("Resource Upload Failed!");
    }
    @ExceptionHandler(ResourceLoadingException.class)
    public ResponseEntity<String> handleFileUploadException(ResourceLoadingException ex) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("Resource Loading Failed!");
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleFileUploadException(NoResourceFoundException ex ) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).header("message", ex.getMessage()).body("(404 Not_Found) Please visit /docs for more information.");
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleFileUploadException(NullPointerException ex ) {
    	logger.warn(ex.getMessage(),ex);
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", ex.getMessage()).body("You may have missed a required value! if you are sure of sending a valid request please contact us!");
    }
    @ExceptionHandler(PasswordResetTokenException.class)
    public ResponseEntity<String> handlePasswordResetTokenException(PasswordResetTokenException ex) {
        logger.warn(ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ex.getMessage()).body(ex.getMessage());
    }
    
    
    
}
