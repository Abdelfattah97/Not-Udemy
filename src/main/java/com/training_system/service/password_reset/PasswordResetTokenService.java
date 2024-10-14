package com.training_system.service.password_reset;

import com.training_system.entity.PasswordResetToken;
import com.training_system.entity.enums.PasswordResetTokenStatus;

public interface PasswordResetTokenService {
	
	PasswordResetToken createPasswordResetToken(String email);

	/**
	 * checks if the token is valid and not expired and returns the status
	 * @param token to validate
	 * @return {@link PasswordResetTokenStatus}
	 */
	PasswordResetTokenStatus validatePasswordResetToken(PasswordResetToken token);
	
	boolean isTokenFound(PasswordResetToken token);
	
	boolean isTokenExpired(PasswordResetToken token);
	
	PasswordResetToken findByToken(String token);

	void changeToUsed(PasswordResetToken passwordResetToken);
	
}
