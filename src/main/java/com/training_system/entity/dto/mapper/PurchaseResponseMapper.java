package com.training_system.entity.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training_system.base.ProductTransaction;
import com.training_system.entity.dto.PurchaseResponse;

@Mapper(componentModel = "spring")
public interface PurchaseResponseMapper {

	@Mapping(source="buyer.id",target="buyerId")
	@Mapping(source="buyer.name",target="buyerName")
	@Mapping(source="payment.currency",target="currency")
	@Mapping(source="payment.id",target="paymentId")
	@Mapping(source="payment.payAmount",target="payAmount")
	@Mapping(source="payment.paymentStatus",target="paymentStatus")
	@Mapping(source="payment.productType",target="productType")
	@Mapping(source="payment.createdDate",target="purchaseDate" , dateFormat = "dd-MM-yyyy HH:mm:ss")
	@Mapping(source="payment.transactionId",target="transactionId")
	@Mapping(source="product.id",target="productId")
	@Mapping(source="product.productTitle",target="productTitle")
	@Mapping(source="id",target="productTransactionId")
	public PurchaseResponse toDto(ProductTransaction enrollment);
	
	default List<PurchaseResponse>toDto(List<ProductTransaction> enrollments){
		return enrollments.stream().map(this::toDto).toList();
	}
	
}
