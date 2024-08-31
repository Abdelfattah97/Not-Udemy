package com.training_system.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.exceptions.DuplicateEnrollmentException;
import com.training_system.repo.PaymentRepo;

import jakarta.persistence.criteria.CriteriaBuilder.Case;

@Service
public class PaymentService extends BaseServiceImpl<Payment, Long> {

	@Autowired
	EnrollmentService enrollmentService;
	
	@Autowired
	PaymentRepo paymentRepo;

	@Autowired
	PaymentProvider stripePayment;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Enrollment payCourse(PaymentRequest paymentRequest) {

		Enrollment prevEnrollment = enrollmentService.findByCourseStudent(
				paymentRequest.getProduct().getId(),
				paymentRequest.getPerson().getId() );
		if(prevEnrollment!=null) {
			String errMsg = String.format(
					"Duplicate enrolls are not Allowed: Student with id: %s is already enrolled in Course with id: %s"
					,paymentRequest.getPerson().getId(),paymentRequest.getProduct().getId());
			logger.error(errMsg);
			throw new DuplicateEnrollmentException(errMsg);
		}
		
		Payment payment = stripePayment.pay(paymentRequest);
		payment = paymentRepo.save(payment);
		
		Course course = new Course();
		course.setId(paymentRequest.getProduct().getId());

		return enrollmentService.enroll(paymentRequest.getPerson(), course, payment, LocalDate.now(),
				determineEnrollmentStatus(payment.getPaymentStatus()));

	}

	EnrollmentStatus determineEnrollmentStatus(PaymentStatus paymentStatus) {
		switch (paymentStatus) {

		case APPROVED: {
			return EnrollmentStatus.REFUNDABLE;
		}
		case FAILED: {
			return EnrollmentStatus.FAILED_PAYMENT;
		}
		case PENDING: {
			return EnrollmentStatus.PENDING_PAYMENT;
		}
		default:
			throw new IllegalArgumentException("Unexpected PaymentStatus value: " + paymentStatus);
		}
	}

}
