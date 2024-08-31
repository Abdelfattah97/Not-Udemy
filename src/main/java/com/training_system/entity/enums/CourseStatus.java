package com.training_system.entity.enums;

public enum CourseStatus  {
	
	PUBLIC(1);
	private CourseStatus(int value) {
		this.value=value;
	}
	private int value;
	
	public int getValue() {
		return value;
	}
	
	public static CourseStatus fromValue(int value) {
		for(CourseStatus courseStatus : CourseStatus.values()) {
			if(courseStatus.getValue() == value) {
				return courseStatus;
			}
		}
		throw new IllegalArgumentException(String.format("No CourseStatus for value: %d",value));
	}
	
}
