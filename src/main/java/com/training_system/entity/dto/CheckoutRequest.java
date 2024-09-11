package com.training_system.entity.dto;

import com.training_system.base.Product;
import com.training_system.entity.enums.ProductType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutRequest {

	private Long productId;
	
	ProductType productType;
	
}
