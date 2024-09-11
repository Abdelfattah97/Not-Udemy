package com.training_system.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.training_system.entity.enums.PaymentMethod;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.exceptions.DuplicateEnrollmentException;
import com.training_system.exceptions.NotRefundableException;
import com.training_system.repo.PaymentRepo;

import jakarta.transaction.Transactional;

@Service
public class PaymentService extends BaseServiceImpl<Payment, Long> {

	@Autowired
	private EnrollmentService enrollmentService;

//	@Autowired
//	EnrollmentRepo enrollmentRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private PaymentProvider stripePayment;

	@Autowired
	private UserService userService;
	
	@Value("${STRIPE_PUBLIC_KEY}")
	private String paymentProviderPublicKey; 

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@PreAuthorize("hasAuthority('master')")
	public List<Payment> findAll() {
		return super.findAll();
	}

	public List<Payment> findAll(UserDetails userDetails) {
		String username = userDetails.getUsername();
		User user = userService.findByUserName(username);
		boolean isAdmin = user.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("master")).findAny()
				.isPresent();
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
	/**
	 * 
	 * @deprecated This method is deprecated and will be removed 
	 * @see {@link PurchaseFacade#purchase(PaymentRequest)}
	 */
	@PreAuthorize("hasAuthority('master') or @paymentService.isUserOwnerOfPayment(#paymentRequest,principal.username)")
	@Transactional
	@Deprecated
	public Enrollment payCourse(PaymentRequest paymentRequest) {
		// Ensures no previous Enrollments preventing Duplicate Enrollments and Payments
		Enrollment prevEnrollment = enrollmentService.findByCourseStudent(paymentRequest.getPerson().getId(),
				paymentRequest.getProduct().getId());
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

		return enrollmentService.enroll(paymentRequest.getPerson(), course, payment);
	}

	// Refunds Related
	@PreAuthorize("hasAuthority('master') or @paymentService.isUserOwnerOfPayment(#payment_id,principal.username)")
	@Transactional
	protected Payment refund(Long payment_id) {
		Payment payment = findById(payment_id);
		// Ensures Payment is refundable before proceeding to a refund request to the
		// payment provider
		if (!isRefundable(payment_id)) {
			throw new NotRefundableException(String.format("Payment with id: %s is Not Refundable", payment_id));
		}
		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		update(payment);
		stripePayment.refund(payment);
		return payment;
	}

	public boolean isRefundable(Long payment_id) {
		return isRefundable(findById(payment_id));
	}

	public boolean isRefundable(Payment payment) {
		return payment.getPaymentStatus().equals(PaymentStatus.REFUNDABLE);
	}

	
	/**
	 * Initializes a new payment based on the provided payment request.
	 * this could be considered as a paymentIntent
	 * @param paymentRequest The payment request containing details such as the buyer, product type,
	 *                       amount, currency, and payment method.
	 * @return The newly created payment entity with the provided details and saved in the database  with status PaymentProcessing.
	 */
	protected Payment intializePayment(PaymentRequest paymentRequest) {
		Payment payment = Payment.builder()
				.buyer(paymentRequest.getPerson())
				.productType(paymentRequest.getProductType())
                .payAmount(paymentRequest.getAmount())
                .currency(paymentRequest.getCurrency())
                .payMethod(PaymentMethod.STRIPE_CHARGE)
                .paymentStatus(PaymentStatus.PayingProcess)
                .transactionId(paymentRequest.getProviderKey())
                .build();
		return insert(payment);
	}

	/**
	 * Proceeds to pay for a given payment request using the provided payment details.
	 * This method integrates with the payment provider to process the payment.
	 * After successful payment, the payment status is updated to REFUNDABLE and the payment
	 * is updated to REFUNDABLE in the database.
	 * 
	 * <b>it is recommended to use this method as the last operation to prevent disputes and refund fees on failures</b>
	 *
	 * @param paymentRequest The payment request containing details such as the buyer, product type,
	 *                       amount, currency, and payment method.
	 * @param payment        The existing payment entity to be updated with the new payment details.
	 *                       This entity is used to maintain the integrity of the payment process.
	 * @return The updated payment entity with the new payment status and saved in the database.
	 */
	@Transactional
	protected Payment ProceedToPay(PaymentRequest paymentRequest,Payment payment) {
		Payment charged = stripePayment.pay(paymentRequest);
		payment.setTransactionId(charged.getTransactionId());
		payment.setPaymentStatus(charged.getPaymentStatus());
		return update(payment);
	}

	public void confirmPayment(Payment payment) {
		payment.setPaymentStatus(PaymentStatus.UNREFUNDABLE);
		 update(payment);
	}
	
	String getPaymentProviderPublicKey() {
		return this.paymentProviderPublicKey;
	}

}
