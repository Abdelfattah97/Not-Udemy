package com.training_system.service.password_reset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training_system.entity.PasswordResetToken;
import com.training_system.entity.enums.PasswordRecoveryMethod;
import com.training_system.service.EmailService;
import com.training_system.service.UserService;

@Component
public class EmailPasswordRecoveryStrategy implements PasswordRecoveryStrategy {

	@Autowired
	EmailService emailService;
	@Autowired
	UserService userService;
	@Autowired
	PasswordResetTokenService resetTokenService;

	String message = "A Password reset was requested for your account , if you didn't request a password reset ignore this message. ";

	@Override
	public String requestPasswordReset(String email) {
		PasswordResetToken resetToken = resetTokenService.createPasswordResetToken(email);
		String body = String.format("%s\ntoken: %s",message,resetToken.getToken());
		
		emailService.sendEmail(email, "Password Reset", body);

		return "Request Submitted";
	}

	@Override
	public PasswordRecoveryMethod getPasswordRecoveryMethod() {
		return PasswordRecoveryMethod.EMAIL;
	}

}
