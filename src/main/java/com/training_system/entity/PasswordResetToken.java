package com.training_system.entity;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

	@Value("PASSWORD_RESET_TOKEN_EXP_MINS")
	@Transient
	@Getter(value = AccessLevel.PRIVATE)
	private int EXPIRATION;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(unique = true)
	private String token;

	private LocalDateTime expire_date;

	private LocalDateTime use_date;

	@PrePersist()
	public void calculateExpireDate() {
		if (expire_date == null)
			this.expire_date = LocalDateTime.now().plusMinutes(EXPIRATION);
	}

}
