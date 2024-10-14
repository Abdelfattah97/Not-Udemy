package com.training_system.exceptions;

public class NoPasswordResetTokenFoundException  extends RuntimeException {

	public NoPasswordResetTokenFoundException(String message) {
		super(message);
	}
	
}
