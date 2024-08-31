package com.training_system.converter;

import com.training_system.entity.enums.ProductType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ProductTypeConverter implements AttributeConverter<ProductType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ProductType attribute) {
		return attribute.getValue();
	}

	@Override
	public ProductType convertToEntityAttribute(Integer dbData) {
		return ProductType.fromValue(dbData);
	}

}
