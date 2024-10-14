package com.training_system.exceptions;

public class AlreadyUsedPasswordResetTokenException extends PasswordResetTokenException {

	public AlreadyUsedPasswordResetTokenException(String message) {
		super(message);
	}
	
}
