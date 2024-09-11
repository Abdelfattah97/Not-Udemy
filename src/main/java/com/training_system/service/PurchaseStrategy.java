package com.training_system.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.training_system.base.ProductTransaction;
import com.training_system.entity.Payment;
import com.training_system.entity.dto.CheckoutRequest;
import com.training_system.entity.dto.CheckoutResponse;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.ProductType;

public interface PurchaseStrategy {

	ProductTransaction pay(PaymentRequest paymentRequest);
	
	Payment refund(Payment payment);
	
	ProductType getProductType();

	CheckoutResponse checkout(UserDetails userDetails, CheckoutRequest checkoutRequest);
	
}
