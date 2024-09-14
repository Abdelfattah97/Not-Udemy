package com.training_system.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.dto.CourseDto;
import com.training_system.entity.dto.LessonDto;
import com.training_system.repo.LessonRepo;
import com.training_system.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {
	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	LessonRepo lessonRepo;

	@GetMapping("/getenrolledcourses/{userId}")
	@PreAuthorize("hasRole('student') or hasRole('master')")
	@PostFilter("hasRole('master') or @enrollmentService.isCourseEnrolled(filterObject.courseId,@userService.getCurrentUser())")
	public Set<CourseDto> getEnrolledCourses(@PathVariable(name = "userId") Long userId) {
		return enrollmentService.getEnrolledCourses(userId);
	}

	@GetMapping("/getenrollments/{userId}/{courseId}")
	@PostFilter("hasRole('master') or @enrollmentService.isCourseEnrolled(filterObject.course_id,@userService.getCurrentUser())")
	public Set<LessonDto> get(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "courseId") Long courseId) {
		return enrollmentService.getEnrolledCourseLessons(userId, courseId);
	}

	@GetMapping("/attendlesson/{lessonId}/{studentId}")
	@PreAuthorize("hasRole('master') or @enrollmentService.isLessonEnrolled(#lessonId,@userService.getCurrentUser())")
	public ResponseEntity<Resource> attendLesson(@PathVariable(name = "lessonId") Long lessonId,
			@PathVariable(name = "studentId") Long studentId) {
		try {
			return enrollmentService.attendLesson(lessonId, studentId);
		} catch (IOException e) {
			String message = "Something wrong happend while getting resource file";
			ByteArrayResource resource = new ByteArrayResource(message.getBytes());

			return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.TEXT_PLAIN).body(resource);
		}
	}
}
