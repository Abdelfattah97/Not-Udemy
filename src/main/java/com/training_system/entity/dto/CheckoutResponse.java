package com.training_system.entity.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.training_system.base.Product;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.ProductType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutResponse {

	private String buyerName;
	
	private Long buyerId;
	
	@JsonIncludeProperties(value = {"id","price","productTitle","productSimpleName"})
	private Product product;
    
    private ProductType productType;
    
    private Currency currency;
    
    private String providerPublicKey;
	
}
