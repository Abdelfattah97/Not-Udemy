package com.training_system.entity.dto;

import java.time.LocalDateTime;

import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;

import lombok.Data;

@Data
public class PaymentDto {

	private Long paymentId;

	private String transactionId;

	private double payAmount;

	private Currency currency;

	private ProductType productType;

	private PaymentStatus paymentStatus;

	private String paymentDate;

	private Long buyerId;

	private String buyerName;

}
