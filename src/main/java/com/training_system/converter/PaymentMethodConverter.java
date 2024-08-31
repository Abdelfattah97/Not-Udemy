package com.training_system.converter;

import com.training_system.entity.enums.PaymentMethod;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, Integer> {

	@Override
	public Integer convertToDatabaseColumn(PaymentMethod attribute) {
		return attribute.getValue();
	}

	@Override
	public PaymentMethod convertToEntityAttribute(Integer dbData) {
		return PaymentMethod.fromValue(dbData);
	}

}
