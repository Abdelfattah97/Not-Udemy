package com.training_system.service.password_reset;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.entity.PasswordResetToken;
import com.training_system.entity.User;
import com.training_system.entity.enums.PasswordResetTokenStatus;
import com.training_system.exceptions.NoPasswordResetTokenFoundException;
import com.training_system.repo.PasswordResetTokenRepository;
import com.training_system.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	@Transactional
	public PasswordResetToken createPasswordResetToken(String email) {

		User user = userService.findByEmail(email);

		String token = UUID.randomUUID().toString();

		PasswordResetToken passwordResetToken = PasswordResetToken.builder().token(token).user(user).build();
		passwordResetTokenRepository.deleteByUser_Id(user.getId());
		passwordResetTokenRepository.flush();
		return passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public PasswordResetTokenStatus validatePasswordResetToken(PasswordResetToken token) {
		if (!isTokenFound(token)) {
			return PasswordResetTokenStatus.NO_TOKEN;
		}
		if (token.getUse_date() != null) {
			return PasswordResetTokenStatus.ALREADY_USED;
		}
		if (isTokenExpired(token)) {
			return PasswordResetTokenStatus.EXPIRED;
		}
		return PasswordResetTokenStatus.VALID;
	}

	@Override
	public boolean isTokenFound(PasswordResetToken token) {
		Objects.requireNonNull(token);
		Objects.requireNonNull(token.getToken(), "token is empty");
		token = passwordResetTokenRepository.findByToken(token.getToken()).orElse(null);
		return token != null;
	}

	@Override
	public boolean isTokenExpired(PasswordResetToken token) {
		return token.getExpire_date().isAfter(LocalDateTime.now());
	}

	@Override
	public PasswordResetToken findByToken(String token) {
		return passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new NoPasswordResetTokenFoundException(
				String.format("No Password Reset Token found with token: %s", token)));
	}

	@Override
	public void changeToUsed(PasswordResetToken passwordResetToken) {
		Objects.requireNonNull(passwordResetToken);
		Long tokenId = passwordResetToken.getId();
		Objects.requireNonNull(tokenId, "Cannot update entity without id.");
		passwordResetToken = passwordResetTokenRepository.findById(passwordResetToken.getId())
				.orElseThrow(() -> new NoPasswordResetTokenFoundException(
						String.format("No Password Reset Token found with id: %s", tokenId)));
		passwordResetToken.setUse_date(LocalDateTime.now());
		passwordResetTokenRepository.save(passwordResetToken);
	}

}
