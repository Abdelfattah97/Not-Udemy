package com.training_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Course;
import com.training_system.entity.dto.CourseDto;
import com.training_system.entity.dto.mapper.CourseDtoMapper;
import com.training_system.service.CourseService;
import com.training_system.service.GuestService;

@RestController
@RequestMapping("/api/course")

public class CourseController  {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GuestService guestService;

	@Autowired
	private CourseDtoMapper courseMapper;

	@PostMapping
	@PreAuthorize("hasRole('master') or (hasAuthority('create_course') and @courseService.isCourseOwner(#courseDto,@userService.getCurrentUser()) )")
	public CourseDto createCourse(@RequestBody CourseDto courseDto) {
		return courseMapper.toDtoSimple(courseService.createCourse(courseMapper.toEntityCourse(courseDto)));
	}

	@GetMapping
	public List<CourseDto> findAll() {
		return courseMapper.toDtoDetailed( courseService.findAll());
	}

	@GetMapping("/{id}")
	public Course findById(@PathVariable Long id) {
		return courseService.findById(id);
	}

	@PutMapping
	@PreAuthorize("hasRole('master') or (hasRole('instructor') and @courseService.isCourseOwner(#courseDto,@userService.getCurrentUser()) )")
	public CourseDto update(@RequestBody CourseDto courseDto) {
		return courseMapper.toDtoSimple(courseService.update(courseMapper.toEntityCourse(courseDto)));
	}
	
	@GetMapping("/public/search/by/title")
	public List<CourseDto> searchContainingTitle(@RequestParam String value){
        return courseMapper.toDtoSimple(guestService.searchByTitle(value));
    }
	@GetMapping("/public/search/by/content")
	public List<CourseDto> searchContainingContent(@RequestParam String value) {
		return courseMapper.toDtoDetailed(guestService.searchByContent(value));
	}
	@GetMapping("/public/search/by/instructor")
	public List<CourseDto> searchByInstructor(@RequestParam String value) {
		return courseMapper.toDtoSimple(guestService.searchByInstructorName(value));
	}

}
