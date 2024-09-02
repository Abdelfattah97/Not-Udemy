package com.training_system.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.User;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;
import com.training_system.exceptions.DuplicateEnrollmentException;
import com.training_system.exceptions.NotRefundableException;
import com.training_system.repo.EnrollmentRepo;
import com.training_system.repo.PaymentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentService extends BaseServiceImpl<Payment, Long> {

	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	EnrollmentRepo enrollmentRepo;
	
	@Autowired
	PaymentRepo paymentRepo;

	@Autowired
	PaymentProvider stripePayment;

	@Autowired
	UserService userService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	//Payments Related
	public Enrollment payCourse(PaymentRequest paymentRequest) {

		//Ensures no previous Enrollments preventing Duplicate Enrollments and Payments
		Enrollment prevEnrollment = enrollmentRepo.findByCourse_IdAndStudent_Id(paymentRequest.getProduct().getId(),
				paymentRequest.getPerson().getId()).orElse(null);
		if (prevEnrollment != null) {
			String errMsg = String.format(
					"Duplicate enrolls are not Allowed: Student with id: %s is already enrolled in Course with id: %s",
					paymentRequest.getPerson().getId(), paymentRequest.getProduct().getId());
			logger.error(errMsg);
			throw new DuplicateEnrollmentException(errMsg);
		}

		Payment payment = stripePayment.pay(paymentRequest);
		payment.setPaymentStatus(PaymentStatus.REFUNDABLE);
		payment = paymentRepo.save(payment);

		Course course = new Course();
		course.setId(paymentRequest.getProduct().getId());

		return enrollmentService.enroll(paymentRequest.getPerson(), course, payment, LocalDate.now(),
				determineEnrollmentStatus(payment.getPaymentStatus()));

	}

	EnrollmentStatus determineEnrollmentStatus(PaymentStatus paymentStatus) {
		switch (paymentStatus) {

		case REFUNDABLE: {
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

	//Refunds Related
	public boolean isUserOwnerOfPayment(Long payment_id, String userName) {

		Payment payment = findById(payment_id);

		User user = userService.findByUserName(userName);

		return payment.getBuyer().getId().equals(user.getPerson().getId());

	}

	public Payment refund(Long payment_id) {
		Payment payment = findById(payment_id);
		// Ensures Payment is refundable before proceeding to a refund request to the
		// payment provider
		if (!isRefundabe(payment_id)) {
			throw new NotRefundableException(String.format("Payment with id: %s is Not Refundable", payment_id));
		}
		Supplier<Payment> afterRefundOperations = preparePruchaseCancellation(payment);
		stripePayment.refund(payment);
		return afterRefundOperations.get();
	}

	private boolean isRefundabe(Long payment_id) {
		return super.findById(payment_id).getPaymentStatus().equals(PaymentStatus.REFUNDABLE);
	}

	private Supplier<Payment> preparePruchaseCancellation(Payment payment) {
		ProductType productType = Objects.requireNonNull(payment.getProductType());

		switch (productType) {
	
			case COURSE_ENROLLMENT_PAYMENT: {
				//Ensures the Enrollment Exists bef proceeding 
				Enrollment enrollment =enrollmentRepo.findByPayment(payment).orElseThrow(()->
				new EntityNotFoundException(
						String.format("No Enrollment found for payment of id: %s",payment.getId())
						));
				
				//This Supplier should be used after the provider refund is completed
				return ()-> {
					// Deletes Enrollment
					enrollmentRepo.deleteById(enrollment.getId());
					// Changes Payment Status
					payment.setPaymentStatus(PaymentStatus.REFUNDED);
					// Update Payment State in DB
					return update(payment);
					};
			}
			default: {
				throw new IllegalArgumentException(
						"Product Type not relating to an operation on cancel, Ensure Payment Service Supports Cancellation of this type!");
			}
		}

	}

}
