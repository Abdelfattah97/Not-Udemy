package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Country;
import com.training_system.entity.Lesson;

public interface LessonRepo extends JpaRepository<Lesson, Long>{

}
