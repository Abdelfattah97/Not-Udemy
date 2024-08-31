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
import com.training_system.entity.Payment;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.PaymentMethod;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.exceptions.PaymentFailureException;

import jakarta.annotation.PostConstruct;

@Component
public final class StripePaymentProviderImpl implements PaymentProvider {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;
	
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
			logger.error("Payment Failed!",e);
			throw new PaymentFailureException(e.getMessage());
		}
		
		// Payment Initialization
		Payment payment = Payment.builder().buyer(chargeRequest.getPerson()).productType(chargeRequest.getProductType())
				.payAmount(chargeRequest.getAmount()).currency(chargeRequest.getCurrency())
				.payMethod(PaymentMethod.STRIPE_CHARGE).paymentStatus(getPaymentStatus(charge.getStatus()))
				.transactionId(charge.getId()).build();
		return payment;
	}

	private PaymentStatus getPaymentStatus(String status) {
		switch (status) {
		case "succeeded":
			return PaymentStatus.APPROVED;
		case "pending":
			return PaymentStatus.PENDING;
		case "failed":
			return PaymentStatus.FAILED;
		default:
			throw new IllegalArgumentException("Can't Determine Payment Status!");
		}
	}

	public Charge charge(PaymentRequest chargeRequest) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("description", chargeRequest.getDescription());
		chargeParams.put("source", chargeRequest.getProviderKey());
		return Charge.create(chargeParams);
	}

}
