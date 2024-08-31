package com.training_system.entity.enums;

public enum EnrollmentStatus {

	CONFIRMED(1),REFUNDABLE(2),FAILED_PAYMENT(3),PENDING_PAYMENT(4);

	private int value;

	private EnrollmentStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static EnrollmentStatus fromValue(int value) {
		for (EnrollmentStatus type : EnrollmentStatus.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("No Enrollment Status with value: %d", value));
	}

}
