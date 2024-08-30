package com.training_system.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Enrollment;
import com.training_system.repo.CourseRepo;
import com.training_system.repo.EnrollmentRepo;
import com.training_system.repo.PersonRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnrollmentService extends BaseServiceImpl<Enrollment, Long>{
	@Autowired
	private EnrollmentRepo enrollmentRepo;
	
	@Autowired
	private PersonRepo personRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
//	public void enroll(Long student_id, Long course_id, Long payment_id, LocalDate enrollmentDate, boolean isConfirmed) {
//		
//		// check that student exists
//		if(personRepo.findById(student_id).isEmpty()) {
//			throw new EntityNotFoundException("Student with id = " + student_id + " Not Found!!!");
//		}
//		if(courseRepo.findById(course_id).isEmpty()) {
//			throw new EntityNotFoundException("Course with id = " + course_id + " Not Found!!!");
//		}
//		Enrollment enrollment = new Enrollment(student_id, course_id, payment_id, enrollmentDate, isConfirmed);
//		
//		enrollmentRepo.save(enrollment);
//	}
}
