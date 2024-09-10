package com.training_system.service;

import com.training_system.entity.Payment;
import com.training_system.entity.enums.ProductType;


public interface ProductConfirmationStrategy {

	Payment confirm(Payment payment);
	
	ProductType getProductType();
}
