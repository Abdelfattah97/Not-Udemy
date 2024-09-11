package com.training_system.base;

import java.time.LocalDate;

import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.enums.ProductType;

public interface ProductTransaction {

	Person getBuyer();

	Product getProduct();

	Payment getPayment();

	LocalDate getTransactiontDate();
	
	ProductType getProductType();

}
