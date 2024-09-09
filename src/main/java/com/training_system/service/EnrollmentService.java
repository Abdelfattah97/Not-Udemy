package com.training_system.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Lesson;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.Question;
import com.training_system.entity.User;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.entity.enums.LessonType;
import com.training_system.exceptions.UnknownStatusException;
import com.training_system.repo.CourseRepo;
import com.training_system.repo.EnrollmentRepo;
import com.training_system.repo.LessonRepo;
import com.training_system.repo.PaymentRepo;
import com.training_system.repo.PersonRepo;
import com.training_system.repo.UserRepo;
import com.training_system.utils.ResourceHandler;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	private UserRepo userRepo;
	
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
		
		
		
		if(lesson.getLessonType() == LessonType.LESSON) {
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
		else {
			if (student.getAttendedLessons().contains(lesson)) { // The student has already attended the quiz
			    String message = "You can access this Quiz only once!";
			    ByteArrayResource resource = new ByteArrayResource(message.getBytes());
			    
			    return ResponseEntity.status(HttpStatus.FORBIDDEN)
			            .contentType(MediaType.TEXT_PLAIN)
			            .body(resource);
			}
			QuestionService qS = new QuestionService();
			Set<Question> questions = qS.getQuizQuestions(lesson_id);
			try {
			    // Convert Set<Question> to JSON
			    ObjectMapper objectMapper = new ObjectMapper();
			    String jsonQuestions = objectMapper.writeValueAsString(questions);
			    
			    // Create ByteArrayResource from JSON string
			    ByteArrayResource resource = new ByteArrayResource(jsonQuestions.getBytes());
			    
			    return ResponseEntity.ok()
			            .contentType(MediaType.APPLICATION_JSON)
			            .body(resource);

			} catch (Exception e) {
			    // Handle serialization error
			    String errorMessage = "Failed to serialize questions";
			    ByteArrayResource errorResource = new ByteArrayResource(errorMessage.getBytes());
			    
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			            .contentType(MediaType.TEXT_PLAIN)
			            .body(errorResource);
			}
		}	
	}
	
	public Set<Course> getEnrolledCourses(Long user_id){
		User user = userRepo.findById(user_id).orElseThrow(() -> new EntityNotFoundException("There is no user with this id!!!"));
		Person student = personRepo.findByUser_Id(user_id).orElseThrow(() -> new EntityNotFoundException("There is no student with this id!!!"));
		
		return enrollmentRepo.findAll().stream()
			    .filter(enrollment -> enrollment.getStudent().getId().equals(student.getId()))
			    .map(enrollment -> courseRepo.findById(enrollment.getCourse().getId()).orElse(null))
			    .filter(Objects::nonNull) // Filter out null values
			    .collect(Collectors.toSet());
	}
	
	public Set<Lesson> getEnrolledCourseLessons(Long user_id, Long course_id){
		User user = userRepo.findById(user_id).orElseThrow(() -> new EntityNotFoundException("There is no user with this id!!!"));
		
		Person student = personRepo.findByUser_Id(user_id).orElseThrow(() -> new EntityNotFoundException("There is no student with this id!!!"));

		Course course = courseRepo.findById(course_id).orElseThrow(() -> new EntityNotFoundException("There is no course with this id!!!"));
		
		return enrollmentRepo.findAll().stream()
			    .filter(enrollment -> enrollment.getStudent().getId().equals(student.getId()))
			    .flatMap(enrollment -> lessonRepo.findByCourse_Id(enrollment.getCourse().getId()).stream())
			    .filter(Objects::nonNull) // Filter out null values if any
			    .collect(Collectors.toSet());
	}
}

