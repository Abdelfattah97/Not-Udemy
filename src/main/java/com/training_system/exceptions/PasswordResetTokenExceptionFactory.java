package com.training_system.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.training_system.entity.enums.PasswordResetTokenStatus;

public class PasswordResetTokenExceptionFactory {

	public static PasswordResetTokenException create(PasswordResetTokenStatus status) {
		switch (status) {
		case ALREADY_USED: {
			return new AlreadyUsedPasswordResetTokenException("Password reset token has already been used.");
		}
		case EXPIRED: {
			return new ExpiredPasswordResetTokenException("Password reset token has expired.");
		}
		case INVALID: {
			return new InvalidPasswordResetTokenException("Password reset token is invalid.");
		}
		case NO_TOKEN:{
			return new NullPasswordResetTokenException("No password reset token was provided.");
		}
		default: {
			return new InvalidPasswordResetTokenException("Error Occured while validating password reset token.");
		}
		}
	}

}
