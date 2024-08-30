package com.training_system.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.exceptions.UnknownStatusException;
import com.training_system.repo.CourseRepo;
import com.training_system.repo.EnrollmentRepo;
import com.training_system.repo.PaymentRepo;
import com.training_system.repo.PersonRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EnrollmentService extends BaseServiceImpl<Enrollment, Long>{
	@Autowired
	private EnrollmentRepo enrollmentRepo;
	
	@Autowired
	private PersonRepo personRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	public void enroll(Person student, Course course, Payment payment, LocalDate enrollmentDate, EnrollmentStatus enrollment_status) {
		Long student_id = student.getId();
		Long course_id = course.getId();
		Long payment_id = payment.getId();
		// check that student exists
		if(personRepo.findById(student_id).isEmpty()) {
			throw new EntityNotFoundException("Student with id = " + student_id + " is Not Found!!!");
		}
		if(courseRepo.findById(course_id).isEmpty()) {
			throw new EntityNotFoundException("Course with id = " + course_id + " is Not Found!!!");
		}
		if(paymentRepo.findById(payment_id).isEmpty()) {
			throw new EntityNotFoundException("Payment with id = " + payment_id + " is Not Found!!!");
		}
		if(enrollment_status.getValue() > 4 || enrollment_status.getValue() < 1) {
			throw new UnknownStatusException("Found unknown enrollment status!!!");
		}
		Enrollment enrollment = new Enrollment(student, course, payment, enrollmentDate, enrollment_status);
		
		enrollmentRepo.save(enrollment);
	}
	
	@Transactional
	public void updateEnrollmentStatus(Payment payment, EnrollmentStatus enrollmentStatus) {
		Long payment_id = payment.getId();
		if(paymentRepo.findById(payment_id).isEmpty()) {
			throw new EntityNotFoundException("Payment with id = " + payment_id + " is Not Found!!!");
		}
		Optional<Enrollment> enrollment = enrollmentRepo.findByPayment(payment);
		if(enrollment.isEmpty()) {
			throw new EntityNotFoundException("Enrollment with payment id = " + payment_id + " is Not Found!!!");
		}
		
		Enrollment enrollmentData = enrollment.get();
		enrollmentData.setEnrollment_status(enrollmentStatus);
		enrollmentRepo.save(enrollmentData);
	}
	
	public void attend() {
		
	}
}
