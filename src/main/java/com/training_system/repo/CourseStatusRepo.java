package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.CourseStatus;

public interface CourseStatusRepo extends JpaRepository<CourseStatus, Long>{

}
