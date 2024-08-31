package com.training_system.converter;

import com.training_system.entity.enums.CourseStatus;
import com.training_system.entity.enums.EnrollmentStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CourseStatusConverter implements AttributeConverter<CourseStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(CourseStatus attribute) {
		return attribute.getValue();
	}

	@Override
	public CourseStatus convertToEntityAttribute(Integer dbData) {
		return CourseStatus.fromValue(dbData);
	}

}
