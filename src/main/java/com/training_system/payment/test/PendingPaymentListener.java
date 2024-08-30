package com.training_system.payment.test;

import com.training_system.entity.Payment;

public interface PendingPaymentListener<T> {

	void onFailAction(Payment payment);
	
	void onSuccessAction(Payment paymnet);
	
}
