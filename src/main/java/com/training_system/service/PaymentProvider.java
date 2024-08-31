package com.training_system.service;

import com.training_system.entity.Payment;
import com.training_system.entity.dto.PaymentRequest;

public interface PaymentProvider {

	<T extends PaymentRequest>Payment pay(T  paymentRequest);
	
}
