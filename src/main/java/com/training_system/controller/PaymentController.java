package com.training_system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Payment;
import com.training_system.entity.dto.ChargeRequest;
import com.training_system.entity.dto.CheckoutRequest;
import com.training_system.entity.dto.CheckoutResponse;
import com.training_system.entity.dto.PaymentDto;
import com.training_system.entity.dto.PurchaseResponse;
import com.training_system.entity.dto.mapper.PaymentDtoMapper;
import com.training_system.entity.dto.mapper.PurchaseResponseMapper;
import com.training_system.entity.enums.ProductType;
import com.training_system.service.PaymentService;
import com.training_system.service.PurchaseFacade;
import com.training_system.service.RefundExpirationManager;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@Autowired
	PaymentService paymentService;

	@Autowired
	PurchaseFacade purchaseFacade;
	
	@Autowired
	RefundExpirationManager refundExpirationManager;
	
	@Autowired
	PurchaseResponseMapper purchaseResponseMapper;
	
	@Autowired
	PaymentDtoMapper paymentDtoMapper;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public List<PaymentDto> findAll(@AuthenticationPrincipal UserDetails userDetails) {
		return paymentDtoMapper.toDto(paymentService.findAll(userDetails));
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('master')")
	public List<PaymentDto> findAll(){
		return paymentDtoMapper.toDto(paymentService.findAll());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('master') or(hasAuthority('view_payment') and @paymentService.isUserOwnerOfPayment(#id,principal.username) )")
	public PaymentDto findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
		return paymentDtoMapper.toDto(paymentService.findById(id));
	}
	
	@PreAuthorize("hasRole('student') or hasRole('master')")
	@GetMapping("/{productTypeName}/{productId}/checkout")
	public CheckoutResponse checkout(@AuthenticationPrincipal UserDetails userDetails, 
	                                 @PathVariable String productTypeName, 
	                                 @PathVariable Long productId) {
	    ProductType productType = ProductType.valueOf(productTypeName.toUpperCase());

	    CheckoutResponse response= purchaseFacade.checkout(userDetails,
	            CheckoutRequest.builder()
	                           .productType(productType)
	                           .productId(productId)
	                           .build());
        return response;
	    
	}
	
	@PostMapping("/purchase/charge")
	@PreAuthorize("hasRole('master') or (hasRole('student') and @paymentService.isUserOwnerOfPayment(#chargeRequest,principal.username) )" ) 
	public PurchaseResponse charge(@ModelAttribute ChargeRequest chargeRequest) {
		logger.warn(chargeRequest.toString());
		return purchaseResponseMapper.toDto(purchaseFacade.purchase(chargeRequest));
	}
	

	@GetMapping("/{payment_id}/refund")
	@PreAuthorize("@paymentService.isUserOwnerOfPayment(#payment_id,principal.username) or hasAuthority('master')")
	public PaymentDto refund(@PathVariable Long payment_id ) {
		Payment payment= new Payment();
		payment.setId(payment_id); 
		return paymentDtoMapper.toDto(purchaseFacade.refund(payment));
	}

	@GetMapping("/limit")
	@PreAuthorize("hasRole('master')")
	public boolean limit() {
		refundExpirationManager.limitExpiredRefundables();
		return true;
	}
	
}
