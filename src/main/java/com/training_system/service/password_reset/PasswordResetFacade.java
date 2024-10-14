package com.training_system.service.password_reset;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training_system.entity.PasswordResetToken;
import com.training_system.entity.User;
import com.training_system.entity.enums.PasswordRecoveryMethod;
import com.training_system.entity.enums.PasswordResetTokenStatus;
import com.training_system.exceptions.NoPasswordResetTokenFoundException;
import com.training_system.exceptions.PasswordResetTokenExceptionFactory;
import com.training_system.exceptions.UnSupportedOperationException;
import com.training_system.service.UserService;

@Component
public class PasswordResetFacade {

	@Autowired
	UserService userService;
	@Autowired
	PasswordResetTokenService resetTokenService;

	Map<PasswordRecoveryMethod, PasswordRecoveryStrategy> methodMap;

	public PasswordResetFacade(@Autowired List<PasswordRecoveryStrategy> strategies) {
		methodMap = strategies.stream().collect(Collectors.toMap(s -> s.getPasswordRecoveryMethod(), s -> s));
	}

	private PasswordRecoveryStrategy strategyFor(PasswordRecoveryMethod method) {
		PasswordRecoveryStrategy strategy = methodMap.get(method);
		if (strategy == null) {
			throw new UnSupportedOperationException(
					String.format("No Implementation for Password Recovery Method: %s", method.toString()));
		}
		return strategy;
	}

	public final String changePassword(String token, String password) {
		PasswordResetToken passwordResetToken;
		try {
			passwordResetToken = resetTokenService.findByToken(token);
		} catch (NoPasswordResetTokenFoundException ex) {
			throw PasswordResetTokenExceptionFactory.create(PasswordResetTokenStatus.INVALID);
		}
		PasswordResetTokenStatus tokenStatus = resetTokenService.validatePasswordResetToken(passwordResetToken);

		// throws the appropriate exception if the token wasn't valid
		if (tokenStatus != PasswordResetTokenStatus.VALID) {
			throw PasswordResetTokenExceptionFactory.create(tokenStatus);
		}
		// if token is valid , proceed to reset the password
		User user = passwordResetToken.getUser();
		user = userService.updatePassword(user, password);
		resetTokenService.changeToUsed(passwordResetToken);
		return "Password Changed";
	}

	public String requestPasswordReset(PasswordRecoveryMethod recoveryMethod, String email) {
		return strategyFor(recoveryMethod).requestPasswordReset(email);
	}

}
