package com.training_system.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.training_system.base.ProductTransaction;
import com.training_system.entity.Payment;
import com.training_system.entity.dto.CheckoutRequest;
import com.training_system.entity.dto.CheckoutResponse;
import com.training_system.entity.dto.PaymentRequest;
import com.training_system.entity.enums.ProductType;
import com.training_system.exceptions.UnSupportedOperationException;

import jakarta.transaction.Transactional;

/**
 * This class acts as a facade for the purchase process, managing the payment,
 * refund, and other related operations.
 */
@Component
public class PurchaseFacade {

	private Map<ProductType, PurchaseStrategy> strategies;

//	@Autowired
	private PaymentService paymentService;

	public PurchaseFacade(List<PurchaseStrategy> strategiesList, @Lazy PaymentService paymentService) {
		this.strategies = strategiesList.stream().collect(Collectors.toMap(PurchaseStrategy::getProductType, s -> s));
		this.paymentService = paymentService;
	}

	@Transactional
	public ProductTransaction purchase(PaymentRequest paymentRequest) {
		ProductType productType = paymentRequest.getProductType();
		return strategyFor(productType).pay(paymentRequest);
	}

	@Transactional
	public Payment refund(Payment payment) {
		payment = paymentService.findById(payment.getId());
		ProductType productType = payment.getProductType();
		return strategyFor(productType).refund(payment);
	}

	public CheckoutResponse checkout(UserDetails userDetails , CheckoutRequest checkoutRequest) {
		ProductType productType = checkoutRequest.getProductType();
		return strategyFor(productType).checkout(userDetails,checkoutRequest);
	}

	PurchaseStrategy strategyFor(ProductType productType) {
		if (!strategies.containsKey(productType)) {
			throw new UnSupportedOperationException(
					String.format("Product Type: %s has not respective confirmation strategy!", productType));
		}
		return strategies.get(productType);
	}

}
