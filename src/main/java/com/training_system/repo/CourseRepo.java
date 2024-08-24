package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Course;

public interface CourseRepo extends JpaRepository<Course,Long>{

}
