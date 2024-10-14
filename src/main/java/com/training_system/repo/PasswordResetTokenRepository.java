package com.training_system.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken, Long>{
	Optional<PasswordResetToken> findByToken(String token);

	int deleteByUser_Id(Long userId);
}
