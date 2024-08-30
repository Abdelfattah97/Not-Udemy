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
	
	public void enroll(Person student, Course course, Payment payment, LocalDate enrollmentDate, boolean isConfirmed) {
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
		Enrollment enrollment = new Enrollment(student, course, payment, enrollmentDate, isConfirmed);
		
		enrollmentRepo.save(enrollment);
	}
	
	@Transactional
	public void updateEnrollment(Payment payment) {
		Long payment_id = payment.getId();
		if(paymentRepo.findById(payment_id).isEmpty()) {
			throw new EntityNotFoundException("Payment with id = " + payment_id + " is Not Found!!!");
		}
		Optional<Enrollment> enrollment = enrollmentRepo.findByPayment(payment);
		if(enrollment.isEmpty()) {
			throw new EntityNotFoundException("Enrollment with id = " + payment_id + " is Not Found!!!");
		}
		
		// Write the code to modify the enrollment status here
		
		Enrollment enrollmentData = enrollment.get();
		enrollmentRepo.save(enrollmentData);
	}
}
