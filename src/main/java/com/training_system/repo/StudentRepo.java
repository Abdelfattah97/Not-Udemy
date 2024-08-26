package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Long>{

}
