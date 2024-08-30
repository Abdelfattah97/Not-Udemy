package com.training_system.payment.test;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(PaymentStatus attribute) {
		return attribute.getValue();
	}

	@Override
	public PaymentStatus convertToEntityAttribute(Integer dbData) {
		return PaymentStatus.fromValue(dbData);
	}

	
}
