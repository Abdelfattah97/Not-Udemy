package com.training_system.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.training_system.entity.dto.CourseDto;
import com.training_system.entity.dto.LessonDto;
import com.training_system.entity.dto.QuestionDto;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.entity.enums.LessonType;
import com.training_system.entity.enums.PaymentStatus;
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
import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter;
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
	
	@Autowired
	@Lazy
	private ProductConfirmationFacade productConfirmationFacade;

	private EnrollmentStatus determineEnrollmentStatus(PaymentStatus paymentStatus) {
		switch (paymentStatus) {

			case REFUNDABLE,PayingProcess: {
				return EnrollmentStatus.REFUNDABLE;
			}
			case FAILED: {
				return EnrollmentStatus.FAILED_PAYMENT;
			}
			case PENDING: {
				return EnrollmentStatus.PENDING_PAYMENT;
			}
			case UNREFUNDABLE:{
				return EnrollmentStatus.CONFIRMED;
			}
			default:
				throw new IllegalArgumentException("Unexpected PaymentStatus value: " + paymentStatus);
		}
	}

	public Enrollment findByCourseStudent(Long course_id , Long person_id) {
		return enrollmentRepo.findByCourse_IdAndStudent_Id(course_id,person_id).orElse(null);
	}
	public Enrollment findByPayment(Payment payment) {
		return enrollmentRepo.findByPayment(payment).orElseThrow(()->
		 new EntityNotFoundException(String.format("No Enrollment Found for payment of id: %s",payment.getId()))
		);
	}
	
	public Enrollment enroll(Person student, Course course, Payment payment) {

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

		EnrollmentStatus enrollmentStatus = determineEnrollmentStatus(payment.getPaymentStatus());
		Enrollment enrollment = new Enrollment(student, course, payment, LocalDate.now(), enrollmentStatus);
		
		return enrollmentRepo.save(enrollment);
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
		Enrollment enrollment = enrollmentRepo.findByCourse_Lessons_IdAndStudent_Id(lesson_id, student_id).orElseThrow(() -> new EntityNotFoundException("There is no enrollment for this course_lesson and student"));
		Payment payment = enrollment.getPayment();
		
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
	        	if(!enrollment.getEnrollment_status().equals(EnrollmentStatus.CONFIRMED)) {
	        		productConfirmationFacade.confirmPayment(payment, null);
	        	}
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
			if(!enrollment.getEnrollment_status().equals(EnrollmentStatus.CONFIRMED)) {
        		productConfirmationFacade.confirmPayment(payment, null);
        	}
			
			QuestionService qS = new QuestionService();
			Set<QuestionDto> questionsDtos = qS.getQuizQuestions(lesson_id);
			try {
			    // Convert Set<Question> to JSON
			    ObjectMapper objectMapper = new ObjectMapper();
			    String jsonQuestions = objectMapper.writeValueAsString(questionsDtos);
			    
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
	
	public Set<CourseDto> getEnrolledCourses(Long user_id){
		User user = userRepo.findById(user_id).orElseThrow(() -> new EntityNotFoundException("There is no user with this id!!!"));
		Person student = personRepo.findByUser_Id(user_id).orElseThrow(() -> new EntityNotFoundException("There is no student with this id!!!"));
		
		Set<Course> courses = enrollmentRepo.findAll().stream()
			    .filter(enrollment -> enrollment.getStudent().getId().equals(student.getId()))
			    .map(enrollment -> courseRepo.findById(enrollment.getCourse().getId()).orElse(null))
			    .filter(Objects::nonNull) // Filter out null values
			    .collect(Collectors.toSet());
		
		return CourseDto.fromEntitiesToDto(courses);
	}
	
	public Set<LessonDto> getEnrolledCourseLessons(Long user_id, Long course_id){
		User user = userRepo.findById(user_id).orElseThrow(() -> new EntityNotFoundException("There is no user with this id!!!"));
		
		Person student = personRepo.findByUser_Id(user_id).orElseThrow(() -> new EntityNotFoundException("There is no student with this id!!!"));

		Course course = courseRepo.findById(course_id).orElseThrow(() -> new EntityNotFoundException("There is no course with this id!!!"));
		
		Set<Lesson> lessons = lessonRepo.findByCourse_IdAndCourse_Enrollments_Student_Id(course_id, student.getId());
		
		return LessonDto.fromEntitiesToDtos(lessons);
	}
	
	public void confrimEnrollment(Payment payment) {
		Enrollment enrollment = enrollmentRepo.findByPayment(payment).orElseThrow(() -> new EntityNotFoundException("There is no enrollment with this payment id!!!"));
		
		enrollment.setEnrollment_status(EnrollmentStatus.CONFIRMED);
		update(enrollment);
	}
	
	public void cancelEnrollment(Payment payment) {
		Enrollment enrollment = enrollmentRepo.findByPayment(payment).orElseThrow(() -> new EntityNotFoundException("There is no enrollment with this payment id!!!"));

		enrollmentRepo.delete(enrollment);
	}
}

