package com.training_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Person;
import com.training_system.entity.User;
import com.training_system.entity.dto.CourseDto;
import com.training_system.entity.enums.CourseStatus;
import com.training_system.exceptions.DuplicateCourseException;
import com.training_system.repo.CourseRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService extends BaseServiceImpl<Course, Long> implements GuestService {

	@Autowired
	LessonService lessonService;

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	PersonService personService;

	@Autowired
	UserService userService;

	public Course update(Course newCourse) {

		if (newCourse.getId() == null) {
			throw new EntityNotFoundException("Trying to update existing record without providing its id");
		}
		Course oldCourse = courseRepo.findById(newCourse.getId()).orElseThrow(() -> new EntityNotFoundException(
				String.format("Cannot Update: No entity found with the id: %s", newCourse.getId())));
		// updating the updatable fields only
		oldCourse.setTitle(newCourse.getTitle());
		oldCourse.setStatus(newCourse.getStatus());
		oldCourse.setPrice(newCourse.getPrice());

		return courseRepo.save(oldCourse); //
	}

	public Course createCourse(Course course) {
		if (courseRepo.existsByTitleAndInstructor_id(course.getTitle(), course.getInstructor().getId())) {
			throw new DuplicateCourseException("There is already a course with this title!");
		}
		Person instructor = personService.findById(course.getInstructor().getId());
		course.setInstructor(instructor);
		return super.insert(course);
	}

	public List<Course> searchContainingTitle(String title) {
		return courseRepo.findByTitleContainsAndStatusAllIgnoreCase(title, CourseStatus.PUBLIC);
	}

	public boolean isCourseOwner(Person instructor, User user) {
		return user.getPerson().getId() == instructor.getId();
	}

	public boolean isCourseOwner(Course course, User user) {
		return isCourseOwner(course.getInstructor(), user);
	}

	public boolean isCourseOwner(CourseDto course, User user) {
		return isCourseOwner(personService.findById(course.getInstructorId()), user);
	}

	@Override
	public List<Course> searchByTitle(String title) {
		return courseRepo.findByTitleContainsAndStatusAllIgnoreCase(title, CourseStatus.PUBLIC);
	}

	@Override
	public List<Course> searchByInstructorName(String instructorNamme) {
		return courseRepo.findByInstructor_NameContainsAndStatusAllIgnoreCase(instructorNamme, CourseStatus.PUBLIC);
	}

	@Override
	public List<Course> searchByContent(String content) {
		return courseRepo.findByTitleContainsOrLessons_TitleContainsAndStatusAllIgnoreCase(content, content,
				CourseStatus.PUBLIC);
	}

}
