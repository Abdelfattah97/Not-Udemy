package com.training_system.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Lesson;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.exceptions.UnknownStatusException;
import com.training_system.repo.CourseRepo;
import com.training_system.repo.EnrollmentRepo;
import com.training_system.repo.LessonRepo;
import com.training_system.repo.PaymentRepo;
import com.training_system.repo.PersonRepo;
import com.training_system.utils.ResourceHandler;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

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
	
	@Autowired
	private LessonRepo lessonRepo;
	
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
		if(enrollmentStatus.getValue() > 4 || enrollmentStatus.getValue() < 1) {
			throw new UnknownStatusException("Found unknown enrollment status!!!");
		}
		Enrollment enrollmentData = enrollment.get();
		enrollmentData.setEnrollment_status(enrollmentStatus);
		enrollmentRepo.save(enrollmentData);
	}
	
	@Transactional
	public ResponseEntity<Resource> attendLesson(Long lesson_id, Long student_id) throws IOException {
		if(lessonRepo.findById(lesson_id).isEmpty()) {
			throw new EntityNotFoundException("Lesson with id = " + lesson_id + " is Not Found!!!");
		}
		if(personRepo.findById(student_id).isEmpty()) {
			throw new EntityNotFoundException("Student with id = " + student_id + " is Not Found!!!");
		}
		
		Person student = personRepo.findById(student_id).get();
		Lesson lesson = lessonRepo.findById(lesson_id).get();
		
		// Load Resource Data (Resource, ContentType)
		ResourceHandler resourceData = new ResourceHandler();
		resourceData.loadResourceData(lesson.getFilePath());
		Resource resource = resourceData.getResource();
		String contentType = resourceData.getContentType();
        // Fallback to application/octet-stream if type is unknown
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
		
		if(!student.getAttendedLessons().contains(lesson)) { // The student has not attended before
			student.addAttendedLesson(lesson);
			personRepo.save(student);
		}
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource); //return resource file MP4, PNG, TXT, PDF
	}
}

