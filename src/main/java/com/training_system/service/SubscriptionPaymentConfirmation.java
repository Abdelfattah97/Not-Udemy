package com.training_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;
import com.training_system.exceptions.IllegalConfirmOperationException;

import lombok.RequiredArgsConstructor;

@Component
public class SubscriptionPaymentConfirmation  implements ProductConfirmationStrategy{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private WalletService walletService;
	
	
	@Override
	public Payment confirm(Payment payment) {
		throw new RuntimeException("Test Strategy");
	}


	@Override
	public ProductType getProductType() {
		return ProductType.SUBSCRIPTION;
	}

}
