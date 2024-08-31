package com.training_system.entity.enums;

public enum PaymentMethod {

	STRIPE_CHARGE(1) , PAYPAL(2);
	
	private PaymentMethod(int value){
		this.value=value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
	
	public static PaymentMethod fromValue(int value) {
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			if(paymentMethod.getValue()==value) {
				return paymentMethod;
			}
		}
		throw new IllegalArgumentException(String.format("No PaymentMethod with value: %d", value));
	}
	
}
