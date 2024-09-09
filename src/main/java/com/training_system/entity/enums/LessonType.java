package com.training_system.entity.enums;

public enum LessonType {
	LESSON(1),QUIZ(2);
	
	private int value;
	
	private LessonType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static LessonType fromValue(int value) {
		for (LessonType type : LessonType.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("No Lesson Type with value: %d", value));
	}
}
