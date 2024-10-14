package com.training_system.exceptions;

public abstract class PasswordResetTokenException extends RuntimeException {

	public PasswordResetTokenException(String message) {
		super(message);
	}
}
