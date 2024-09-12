package com.training_system.service;

import com.training_system.entity.dto.CourseDto;
import com.training_system.exceptions.DuplicateCourseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Lesson;
import com.training_system.entity.Person;
import com.training_system.exceptions.UnAuthorizedException;
import com.training_system.repo.CourseRepo;
import com.training_system.repo.PersonRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService extends BaseServiceImpl<Course, Long> {
	
	
	@Autowired
	LessonService lessonService;
	
	@Autowired
	CourseRepo courseRepo;

	@Autowired
	PersonRepo personRepo;

	@Autowired
	UserService userService;


	public void addCourse(CourseDto courseDto) {
		if(courseRepo.findByTitle(courseDto.getTitle()).isPresent()){
			throw new DuplicateCourseException("There is already a course with this title!!!");
		}
		
		Person instructor = personRepo.findById(courseDto.getInstructor_id()).orElseThrow(() -> new EntityNotFoundException("There is no instructor with this id."));

		if (!userService.isInstructor(instructor.getUser()))
			throw new UnAuthorizedException("User doesn't have a role of an instructor to create courses");

		Course course = new Course();
		course.setInstructor(instructor);
		course.setTitle(courseDto.getTitle());
		course.setStatus(courseDto.getStatus());
		course.setPrice(courseDto.getPrice());
		courseRepo.save(course);
	}
}
