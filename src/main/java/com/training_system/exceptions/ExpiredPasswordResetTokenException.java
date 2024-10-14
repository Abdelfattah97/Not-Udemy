package com.training_system.exceptions;

public class ExpiredPasswordResetTokenException extends PasswordResetTokenException {

	public ExpiredPasswordResetTokenException(String message) {
		super(message);
	}

}
