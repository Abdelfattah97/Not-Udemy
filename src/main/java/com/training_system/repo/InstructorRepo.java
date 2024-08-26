package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Instructor;

public interface InstructorRepo extends JpaRepository<Instructor,Long>{

}
