package com.training_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.enums.ProductType;
import com.training_system.exceptions.IllegalConfirmOperationException;

import jakarta.transaction.Transactional;

@Component
public class EnrollmentPaymentConfirmation implements ProductConfirmationStrategy {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private WalletService walletService;

	@Value("${INSTRUCTOR_ENROLLMENT_REVENUE_RATIO}")
	private Double instructorRevenuePerEnrollment;
	
	@Override
	@Transactional
	public Payment confirm(Payment payment) {
		Person instructor = null;
		Enrollment enrollment = null;
		try {
			enrollment = enrollmentService.findByPayment(payment);
			instructor = enrollment.getCourse().getInstructor();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new IllegalConfirmOperationException(String.format("Confirmation Failed: %s", ex.getMessage()));
		}
		enrollmentService.confrimEnrollment(enrollment);
		paymentService.confirmPayment(payment);
		walletService.deposit(instructor, payment.getPayAmount());
		return paymentService.update(payment);
	}

	@Override
	public ProductType getProductType() {
		return ProductType.COURSE_ENROLLMENT;
	}
	
	private Integer calculateRevenue(Payment payment) {
		return  (payment.getPayAmount()*(int)(instructorRevenuePerEnrollment*100));
	}

}
