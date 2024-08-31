package com.training_system.entity.dto;

import com.training_system.base.Product;
import com.training_system.entity.Person;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.ProductType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChargeRequest implements PaymentRequest {
	
	
	private Person person;
	private Product product;
	private String description;
	private Integer amount;
	private Currency currency;
	private String stripeEmail;
	private String stripeToken;
	private ProductType productType;
	
	

	@Override
	public String getProviderKey() {
		
		return this.getStripeToken();
	}





	
	
	
}
