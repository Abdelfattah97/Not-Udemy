package com.training_system.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
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

	@Override
	@PreAuthorize("hasAuthority('master')")
	public List<Payment> findAll() {
		return super.findAll();
	}

	public List<Payment> findAll(UserDetails userDetails) {
		String username = userDetails.getUsername();
		logger.warn("UserName Intiallized from user details");
		User user = userService.findByUserName(username);
		logger.warn("retrieved user through user service");
		boolean isAdmin = user.getRoles().stream()
				.filter(role -> role.getName().equalsIgnoreCase("master"))
				.findAny()
				.isPresent();		
		logger.warn("Determined if user is Admin: "+isAdmin);
		if (isAdmin) {
			return findAll();
		} else {
			return findAllByUser(user);
		}
	}

	@Override
	@PreAuthorize("hasAuthority('master') or @paymentService.isUserOwnerOfPayment(#id,principal.username)")
	public Payment findById(Long id) {
		return super.findById(id);
	}

	private List<Payment> findAllByUser(User user) {
		Person buyer = Objects.requireNonNull(user.getPerson());
		return paymentRepo.findByBuyer(buyer);
	}

	public boolean isUserOwnerOfPayment(Long payment_id, String userName) {
		Payment payment = findById(payment_id);
		User user = userService.findByUserName(userName);
		return payment.getBuyer().getId().equals(user.getPerson().getId());
	}
	public boolean isUserOwnerOfPayment(PaymentRequest paymentRequest, String userName) {
		Long person_id = paymentRequest.getPerson().getId();
		User user = userService.findByUserName(userName);
		return person_id.equals(user.getPerson().getId());
	}
	
	// Payments Related
	@PreAuthorize("hasAuthority('master') or @paymentService.isUserOwnerOfPayment(#paymentRequest,principal.username)")
	public Enrollment payCourse(PaymentRequest paymentRequest) {
		
		// Ensures no previous Enrollments preventing Duplicate Enrollments and Payments
		Enrollment prevEnrollment = enrollmentRepo
				.findByCourse_IdAndStudent_Id(paymentRequest.getProduct().getId(), paymentRequest.getPerson().getId())
				.orElse(null);
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

	// Refunds Related
	@PreAuthorize("hasAuthority('master') or @paymentService.isUserOwnerOfPayment(#payment_id,principal.username)")
	public Payment refund(Long payment_id) {
		Payment payment = findById(payment_id);
		// Ensures Payment is refundable before proceeding to a refund request to the payment provider
		if (!isRefundable(payment_id)) {
			throw new NotRefundableException(String.format("Payment with id: %s is Not Refundable", payment_id));
		}
		Supplier<Payment> afterRefundOperations = preparePruchaseCancellation(payment);
		stripePayment.refund(payment);
		return afterRefundOperations.get();
	}

	public boolean isRefundable(Long payment_id) {
		return isRefundable(findById(payment_id));
	}

	public boolean isRefundable(Payment payment) {
		return payment.getPaymentStatus().equals(PaymentStatus.REFUNDABLE);
	}

	private Supplier<Payment> preparePruchaseCancellation(Payment payment) {
		ProductType productType = Objects.requireNonNull(payment.getProductType());

		switch (productType) {

		case COURSE_ENROLLMENT_PAYMENT: {
			// Ensures the Enrollment Exists bef proceeding
			Enrollment enrollment = enrollmentRepo.findByPayment(payment).orElseThrow(() -> new EntityNotFoundException(
					String.format("No Enrollment found for payment of id: %s", payment.getId())));

			// This Supplier should be used after the provider refund operation is completed
			// successfully
			return () -> {
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

	public void limitExpiredRefundables() {
		Set<Enrollment> enrollments = new HashSet<Enrollment>();
		List<Payment> payments = paymentRepo.findAll().stream()
				.filter(p -> isRefundable(p) && exceededRefundableDuration(p)).map(p -> {
					logger.warn(String.format("Changed status of payment with id: %s ", p.getId()));
					p.setPaymentStatus(PaymentStatus.UNREFUNDABLE);
					Enrollment enrollment = enrollmentService.findByPayment(p);
					enrollment.setEnrollment_status(determineEnrollmentStatus(p.getPaymentStatus()));
					enrollments.add(enrollment);
					return p;
				}).toList();
		paymentRepo.saveAllAndFlush(payments);
		enrollmentRepo.saveAllAndFlush(enrollments);
	}

	private boolean exceededRefundableDuration(Payment payment) {
		LocalDateTime creationDate = payment.getCreatedDate();
		Duration paymentAge = Duration.between(creationDate, LocalDateTime.now());
		Duration refundLimit = Duration.ofMinutes(1L);
		return paymentAge.compareTo(refundLimit) > 0;
	}

}
