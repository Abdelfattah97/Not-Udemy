package com.training_system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Course;
import com.training_system.entity.enums.CourseStatus;

@Repository
public interface CourseRepo extends BaseRepository<Course,Long>{

    Optional<Course> findByTitle(String title);
    boolean existsByTitleAndInstructor_id(String title,Long instructionId);
    
    List<Course> findByTitleContainsAndStatusAllIgnoreCase(String title,CourseStatus status);
    
    List<Course> findByInstructor_NameContainsAndStatusAllIgnoreCase(String instructorName,CourseStatus status);
    
    List<Course> findByTitleContainsOrLessons_TitleContainsAndStatusAllIgnoreCase(String CourseTitle,String lessonTitle,CourseStatus status);
    
    
    
}
