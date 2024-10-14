package com.training_system.exceptions;

public class InvalidPasswordResetTokenException extends PasswordResetTokenException {

	public InvalidPasswordResetTokenException(String message) {
        super(message);
    }
	
}
