package com.training_system.entity.enums;

public enum Currency  {
	
	USD(1),EUR(2);
	private Currency(int value) {
		this.value=value;
	}
	private int value;
	
	public int getValue() {
		return value;
	}
	
	public static Currency fromValue(int value) {
		for(Currency courseStatus : Currency.values()) {
			if(courseStatus.getValue() == value) {
				return courseStatus;
			}
		}
		throw new IllegalArgumentException(String.format("No CourseStatus for value: %d",value));
	}
	
}
