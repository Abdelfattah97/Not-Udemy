package com.training_system.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Lesson;

@Repository
public interface LessonRepo extends BaseRepository<Lesson, Long>{
	Optional<Lesson> findByCourse_IdAndStudent_Id(Long course_id, Long student_id);
}
