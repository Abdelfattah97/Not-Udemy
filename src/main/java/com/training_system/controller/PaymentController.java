package com.training_system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Course;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.dto.ChargeRequest;
import com.training_system.entity.dto.CheckoutRequest;
import com.training_system.entity.dto.CheckoutResponse;
import com.training_system.entity.dto.PaymentDto;
import com.training_system.entity.dto.PurchaseResponse;
import com.training_system.entity.dto.mapper.PaymentDtoMapper;
import com.training_system.entity.dto.mapper.PurchaseResponseMapper;
import com.training_system.entity.enums.Currency;
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
	
	@GetMapping("/{id}")
	public PaymentDto findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
		return paymentDtoMapper.toDto(paymentService.findById(id));
	}
	
	@PreAuthorize("hasAuthority('student')")
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
//	    logger.warn(response.toString());
        return response;
	    
	}
	
	@PostMapping("/purchase/charge")
	public PurchaseResponse charge(@RequestParam(name = "amount") Integer amountCents, @RequestParam String stripeToken,
			@RequestParam String stripeTokenType, @RequestParam String stripeEmail, @RequestParam Long person_id,
			@RequestParam Long course_id) {
		Person person = new Person();
		person.setId(person_id);
		Course course = new Course();
		course.setId(course_id);

		ChargeRequest chargeRequest = ChargeRequest.builder().person(person).product(course)
				.description("Course Purchase").amount(amountCents).currency(Currency.USD).stripeEmail(stripeEmail)
				.stripeToken(stripeToken).productType(ProductType.COURSE_ENROLLMENT).build();

		return purchaseResponseMapper.toDto(purchaseFacade.purchase(chargeRequest));
	}

	@PreAuthorize("@paymentService.isUserOwnerOfPayment(#payment_id,principal.username) or hasAuthority('master')")
	@GetMapping("/{payment_id}/refund")
	public PaymentDto refund(@PathVariable Long payment_id ) {
		Payment payment= new Payment();
		payment.setId(payment_id); 
		return paymentDtoMapper.toDto(purchaseFacade.refund(payment));
	}

	@GetMapping("/limit")
	public boolean limit() {
		refundExpirationManager.limitExpiredRefundables();
		return true;
	}
	
}
