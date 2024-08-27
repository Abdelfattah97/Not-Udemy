package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Lesson;
import com.training_system.repo.CourseRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService extends BaseServiceImpl<Course, Long> {

	@Autowired
	LessonService lessonService;
	@Autowired
	CourseRepo courseRepo;
	
	Lesson addLesson(Course course ,Lesson lesson) {
	course.addLesson(lesson);
	courseRepo.save(course);
	return lessonService.insert(lesson);
	}
	
	public Lesson addLesson(Long course_id ,Lesson lesson) {
		Course course = courseRepo.findById(course_id) 
				.orElseThrow(()-> new EntityNotFoundException(String.format("No Course found with id: %s to add a lesson", course_id)));
		return addLesson(course, lesson);
	}
	
	
}
