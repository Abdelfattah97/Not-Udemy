package com.training_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training_system.base.ProductTransaction;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.ProductType;
import com.training_system.exceptions.DuplicateEnrollmentException;
import com.training_system.exceptions.RefundFailureException;

import jakarta.transaction.Transactional;

@Component
public class EnrollmentPurchaseStrategy implements PurchaseStrategy {

	@Autowired
	private EnrollmentService enrollmentService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private ProductConfirmationFacade productConfirmationFacade;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional
	public ProductTransaction pay(PaymentRequest paymentRequest) {
		
		// Ensures no previous Enrollments to prevent Duplicate Enrollments and Payments
		duplicateEnrollmentCheck(paymentRequest);
		
		Course course = new Course();
		course.setId(paymentRequest.getProduct().getId());
		
		// Confirming the enrollment before proceeding with the payment process.
		Payment payment = paymentService.intializePayment(paymentRequest);
		Enrollment enrollment= enrollmentService.enroll(paymentRequest.getPerson(), course, payment);
		
		// should keep it last one to prevent disputes and refunds fees
		payment = paymentService.ProceedToPay(paymentRequest,payment); 
		return enrollment;
	}

	@Override
	@Transactional
	public Payment refund(Payment payment) {
		Enrollment enrollment = enrollmentService.findByPayment(payment);
		if(enrollment == null) {
			throw new RefundFailureException(String.format("No Enrollment found for payment with id: %s", payment.getId()));
		}
		enrollmentService.cancelEnrollment(payment);
		return paymentService.refund(payment.getId());
		
	}
	
	private void duplicateEnrollmentCheck(PaymentRequest paymentRequest){
		Enrollment prevEnrollment =enrollmentService.findByCourseStudent(paymentRequest.getProduct().getId(),paymentRequest.getPerson().getId());
		if (prevEnrollment != null) {
			String errMsg = String.format(
					"Duplicate enrolls are not Allowed: Student with id: %s is already enrolled in Course with id: %s",
					paymentRequest.getPerson().getId(), paymentRequest.getProduct().getId());
			logger.error(errMsg);
			throw new DuplicateEnrollmentException(errMsg);
		}
	}

	@Override
	public ProductType getProductType() {
		return ProductType.COURSE_ENROLLMENT;
	}

	
	
}
