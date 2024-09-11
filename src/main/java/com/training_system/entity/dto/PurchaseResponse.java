package com.training_system.entity.dto;

import java.time.LocalDateTime;

import com.training_system.base.Product;
import com.training_system.entity.Person;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;

import lombok.Data;

@Data
public class PurchaseResponse {

	private Long paymentId;
	
	private String transactionId;
	
	private double payAmount;
	
	private Currency currency;
	
	private ProductType productType;
	
	private Long productId;
	
	private String productTitle;
	
	private Long productTransactionId;
	
	private PaymentStatus paymentStatus;

	private String purchaseDate;
	
	private Long buyerId ;
	
	private String buyerName;
	
}
