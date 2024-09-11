package com.training_system.entity.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training_system.entity.Payment;
import com.training_system.entity.dto.PaymentDto;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

	@Mapping(source="buyer.id",target="buyerId")
	@Mapping(source="buyer.name",target="buyerName")
	@Mapping(source="payment.currency",target="currency")
	@Mapping(source="payment.id",target="paymentId")
	@Mapping(source="payment.payAmount",target="payAmount")
	@Mapping(source="payment.paymentStatus",target="paymentStatus")
	@Mapping(source="payment.productType",target="productType")
	@Mapping(source="payment.createdDate",target="paymentDate" , dateFormat = "yyyy/MM/dd 'T'HH:mm:ss")
	@Mapping(source="payment.transactionId",target="transactionId")
	public PaymentDto toDto(Payment payment);
	
	default List<PaymentDto>toDto(List<Payment> payments){
		return payments.stream().map(this::toDto).toList();
	}
	
}
