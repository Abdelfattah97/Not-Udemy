package com.training_system.converter;

import com.training_system.entity.enums.LessonType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class LessonTypeConverter implements AttributeConverter<LessonType, Integer>{
	@Override
	public Integer convertToDatabaseColumn(LessonType attribute) {
		return attribute.getValue();
	}
	
	@Override
	public LessonType convertToEntityAttribute(Integer dbData) {
		return LessonType.fromValue(dbData);
	}
}
