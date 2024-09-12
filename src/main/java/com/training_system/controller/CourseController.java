package com.training_system.controller;

import com.training_system.entity.dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseAbstractController;
import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Lesson;
import com.training_system.service.CourseService;

@RestController
@RequestMapping("/api/course")

public class CourseController extends BaseControllerImpl<Course, Long> {

	@Autowired
	private CourseService courseService ;

	@PostMapping("/addcourse")
	public void addCourse(@RequestBody CourseDto courseDto){
		courseService.addCourse(courseDto);
	}
}
