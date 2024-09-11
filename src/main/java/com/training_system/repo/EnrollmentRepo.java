package com.training_system.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.training_system.base.BaseRepository;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import java.util.List;


@Repository
public interface EnrollmentRepo extends BaseRepository<Enrollment, Long> {
	Optional<Enrollment> findByPayment(Payment payment);
	Optional<Enrollment> findByCourse_IdAndStudent_Id(Long course_id,Long student_id);
	Optional<Enrollment> findByCourse_Lessons_IdAndStudent_Id(Long lesson_id, Long student_id);
}
