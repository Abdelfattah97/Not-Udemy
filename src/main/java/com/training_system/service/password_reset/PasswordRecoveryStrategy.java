package com.training_system.service.password_reset;

import com.training_system.entity.enums.PasswordRecoveryMethod;

public interface PasswordRecoveryStrategy {

	/**
	 * 
	 * @param email email to identify the user
	 * @return
	 */
	String requestPasswordReset(String email);

	PasswordRecoveryMethod getPasswordRecoveryMethod();
	
}
