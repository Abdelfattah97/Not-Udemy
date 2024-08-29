package com.training_system.entity.enums;

public enum UserType {

	INSTRUCTOR(1),STUDENT(2);

	private int value;

	private UserType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static UserType fromValue(int value) {
		for (UserType type : UserType.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("No UserType with value: %d", value));
	}

}
