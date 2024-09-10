package com.training_system.service;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training_system.entity.Payment;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;
import com.training_system.exceptions.IllegalConfirmOperationException;
import com.training_system.exceptions.UnSupportedOperationException;

import jakarta.transaction.Transactional;

@Component
public class ProductConfirmationFacade {

	private Map<ProductType, ProductConfirmationStrategy> Strategies;

	public ProductConfirmationFacade(List<ProductConfirmationStrategy> productConfirmationStrategies) {
		this.Strategies = productConfirmationStrategies.stream()
				.collect(Collectors.toMap(ProductConfirmationStrategy::getProductType, p -> p));
	}

	/**
	 * this method determines the product type and calls its respective confirm
	 * method this method is transactional which means any exception will rollback
	 * all changes done in db
	 * 
	 * @param payment        the payment to be confirmed
	 * @param laterOperation an extra operation if needed after the confirmation,
	 *                       can be null if no further operation needed
	 * @return the confirmed payment,never null
	 */
	@Transactional
	public Payment confirmPayment(Payment payment, Supplier<Object> laterOperation) {
		if (!payment.getPaymentStatus().equals(PaymentStatus.REFUNDABLE)) {
			throw new IllegalConfirmOperationException(
					String.format("Payment Status is: %s, Confirmation Rejected!", payment.getPaymentStatus()));
		}

		ProductType productType = payment.getProductType();
		
		Payment confirmedPayment = strategyFor(productType).confirm(payment);

		if (laterOperation != null)
			laterOperation.get();
		return confirmedPayment;
	}
	
	private ProductConfirmationStrategy strategyFor(ProductType productType) {
		if(!Strategies.containsKey(productType)) {
			throw new UnSupportedOperationException(String.format("Product Type: %s has not respective confirmation strategy!", productType));
		}
		return Strategies.get(productType);
	}

}
