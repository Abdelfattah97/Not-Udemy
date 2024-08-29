package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Lesson;
import com.training_system.repo.CourseRepo;
import com.training_system.repo.PersonRepo;

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

	public Course addLesson(Course course, Lesson lesson) {
//		course.addLesson(lesson);
//		lessonService.insert(lesson);
//		return courseRepo.save(course);
		
		return null ;//placeholder
	}

	public Course addLesson(Long course_id, Lesson lesson) {
//		Course course = courseRepo.findById(course_id).orElseThrow(() -> new EntityNotFoundException(
//				String.format("No Course found with id: %s to add a lesson", course_id)));
//		return addLesson(course, lesson);
		
		return null ;//placeholder
	}

	
	@Override
	public Course insert(Course course) {
//		insertionCheck(course);
//		
//		Person instructor = course.getInstructor();
//		
//		if (instructor == null || instructor.getId() == null)
//			throw new IllegalArgumentException("Instructor id is needed when creating a course");
//
//		Long instructor_id = instructor.getId();
//		
//		instructor = personRepo.findById(instructor.getId())
//				.orElseThrow(
//				() ->new EntityNotFoundException(
//						String.format("No Instructor Found with id: %s", instructor_id)
//						));
//
//		if (!userService.isInstructor(instructor.getUser()))
//			throw new UnAuthorizedException("User doesn't have a role of an instructor to create courses");
//
//		
//		course.setInstructor(instructor);
//
//		return super.insert(course);
		
		return null;//placeholder
	}

}
