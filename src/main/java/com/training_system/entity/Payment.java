package com.training_system.entity;

import com.training_system.base.BaseEntity;
import com.training_system.converter.PaymentMethodConverter;
import com.training_system.converter.ProductTypeConverter;
import com.training_system.entity.enums.PaymentMethod;
import com.training_system.entity.enums.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity<Long> {

	@Convert(converter = PaymentMethodConverter.class)
	private PaymentMethod payMethod;

	@Column(nullable = false)
	private Double payAmount;

	@Convert(converter = ProductTypeConverter.class)
	private ProductType productType;
	
	
	private String transaction_id;
	
}
