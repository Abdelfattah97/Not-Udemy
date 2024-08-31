package com.training_system.entity.dto;


import com.training_system.base.Product;
import com.training_system.entity.Person;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.ProductType;

import lombok.Data;


public interface PaymentRequest {

	/**
	 * this amount is in cents which means it is divided by 100
	 */
	public Integer getAmount();
	
	public Currency getCurrency();

	public String getDescription();
	
	public String getProviderKey();
	
	public Person getPerson();
	
	public Product getProduct();
	
	public ProductType getProductType();
	
}
