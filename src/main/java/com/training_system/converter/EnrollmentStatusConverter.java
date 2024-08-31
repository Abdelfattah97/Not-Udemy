package com.training_system.converter;

import com.training_system.entity.enums.EnrollmentStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EnrollmentStatusConverter implements AttributeConverter<EnrollmentStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(EnrollmentStatus attribute) {
		return attribute.getValue();
	}

	@Override
	public EnrollmentStatus convertToEntityAttribute(Integer dbData) {
		return EnrollmentStatus.fromValue(dbData);
	}

}
