package com.training_system.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.training_system.base.BaseRepository;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;

@Repository
public interface EnrollmentRepo extends BaseRepository<Enrollment, Long> {
	Optional<Enrollment> findByPayment(Payment payment);
}
