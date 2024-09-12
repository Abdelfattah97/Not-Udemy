package com.training_system.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.PaymentMethod;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.exceptions.PaymentFailureException;
import com.training_system.exceptions.RefundFailureException;

import jakarta.annotation.PostConstruct;

@Component
public final class StripePaymentProviderImpl implements PaymentProvider {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;
	
	@Value("${STRIPE_REFUNDABLE_RATIO}")
	private Double refundableRatio;

	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
	}

	@Override
	public Payment pay(PaymentRequest chargeRequest) {
		// Charge Response
		Charge charge = null;
		try {
			charge = charge(chargeRequest);
		} catch (RuntimeException | AuthenticationException | InvalidRequestException | APIConnectionException
				| CardException | APIException e) {
			logger.error("Payment Failed!", e);
			throw new PaymentFailureException(e.getMessage());
		}

		// Payment Initialization
		Person buyer = new Person();
		buyer.setId(chargeRequest.getPersonId());
		Payment payment = Payment.builder().buyer(buyer).productType(chargeRequest.getProductType())
				.payAmount(chargeRequest.getAmount()).currency(chargeRequest.getCurrency())
				.payMethod(PaymentMethod.STRIPE_CHARGE).paymentStatus(getPaymentStatus(charge.getStatus()))
				.transactionId(charge.getId()).build();
		return payment;
	}

	@Override
	public Payment refund(Payment payment) {

		// Retrieve Previous Charge
		Charge charge = null;
		try {
			charge = Charge.retrieve(payment.getTransactionId());
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			logger.error(e.getMessage(), e);
			throw new RefundFailureException(
					String.format("Can't Retrieve previous charge(%s)", payment.getTransactionId()));
		}

		// Make a Refund
		Refund refund = null;
		try {
			refund = refund(charge);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			logger.error(e.getMessage(), e);
			throw new RefundFailureException(e.getMessage());
		}

		// Payment Object Update
		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		return payment;

	}

	private PaymentStatus getPaymentStatus(String status) {
		switch (status) {
		case "succeeded":
			return PaymentStatus.REFUNDABLE;
		case "pending":
			return PaymentStatus.PENDING;
		case "failed":
			return PaymentStatus.FAILED;
		default:
			throw new IllegalArgumentException("Can't Determine Payment Status!");
		}
	}

	private Charge charge(PaymentRequest chargeRequest) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("description", chargeRequest.getDescription());
		chargeParams.put("source", chargeRequest.getProviderKey());
		return Charge.create(chargeParams);
	}

	private Refund refund(Charge charge) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		Map<String, Object> refundParams = new HashMap<String, Object>();
		refundParams.put("amount", calculateRefundFees(charge.getAmount()));
		refundParams.put("charge", charge.getId());

		return Refund.create(refundParams);
	}
	
	private Integer calculateRefundFees(Long amount) {
		
		return (int) Math.ceil(amount*refundableRatio);
		
	}

}
