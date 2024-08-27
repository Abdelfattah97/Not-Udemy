package com.training_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Lesson;
import com.training_system.service.CourseService;

@RestController
@RequestMapping("/api/course")
public class CourseController extends BaseControllerImpl<Course, Long> {

	
	@Autowired
	private CourseService courseService ;
	
	@PostMapping("/{course_id}")
	Lesson addLesson( @PathVariable Long course_id,@RequestBody Lesson lesson ) {
		return courseService.addLesson(course_id,lesson);
	}

	@Override
	@GetMapping
	public List<Course> findAll() {
		return super.findAll();
	}

	@Override
	@GetMapping("/{id}")
	public Course findById(@PathVariable Long id) {
		return super.findById(id);
	}

	@Override
	@PostMapping()
	public Course Insert(@RequestBody Course entity) {
		return super.Insert(entity);
	}

	@Override
	@PutMapping
	public Course update(@RequestBody Course entity) {
		return super.update(entity);
	}

	
	
}
