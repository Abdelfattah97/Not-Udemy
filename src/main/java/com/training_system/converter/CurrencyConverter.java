package com.training_system.converter;

import com.training_system.entity.enums.Currency;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CurrencyConverter implements AttributeConverter<Currency, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Currency attribute) {
		return attribute.getValue();
	}

	@Override
	public Currency convertToEntityAttribute(Integer dbData) {
		return Currency.fromValue(dbData);
	}

}
