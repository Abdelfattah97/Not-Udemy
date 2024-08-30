package com.training_system.payment.test;

public enum PaymentStatus {

	PENDING(1), DECLINED(2), APPROVED(3);

	private PaymentStatus(int value) {
		this.value = value;
	}

	private int value;

	public int getValue() {
		return this.value;
	}
	
	public static PaymentStatus fromValue(int value){
		for(PaymentStatus status : values()) {
			if(status.getValue()==value) {
				return status;
			}
		}
		throw new IllegalArgumentException(String.format("No PaymentStatus for Value: %d",value));
	}

}
