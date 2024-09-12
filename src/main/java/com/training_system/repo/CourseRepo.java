package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepo extends BaseRepository<Course,Long>{

    Optional<Course> findByTitle(String title);
}
