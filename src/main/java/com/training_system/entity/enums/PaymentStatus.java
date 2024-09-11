package com.training_system.entity.enums;

public enum PaymentStatus {

	PENDING(1), FAILED(2), REFUNDABLE(4), UNREFUNDABLE(5), REFUNDED(6) , PayingProcess(7);

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
