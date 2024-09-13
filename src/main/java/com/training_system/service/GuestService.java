package com.training_system.service;

import java.util.List;

import com.training_system.entity.Course;

public interface GuestService {

	List<Course> searchByTitle(String title);
	List<Course> searchByInstructorName(String instructorNamme);
	List<Course> searchByContent(String content);
	
}
