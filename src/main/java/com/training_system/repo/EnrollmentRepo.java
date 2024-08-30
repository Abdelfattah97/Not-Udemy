package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Enrollment;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

}
