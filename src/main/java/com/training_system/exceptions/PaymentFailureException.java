package com.training_system.exceptions;

public class PaymentFailureException extends RuntimeException{

	public PaymentFailureException(String messsage) {
		super(messsage);
	}
	
}
