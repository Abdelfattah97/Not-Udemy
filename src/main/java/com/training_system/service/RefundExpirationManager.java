package com.training_system.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training_system.entity.Payment;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.repo.PaymentRepo;

@Component
public class RefundExpirationManager {
	
	@Autowired
	PaymentRepo paymentRepo;
	@Autowired
	ProductConfirmationFacade productConfirmationFacade;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void limitExpiredRefundables() {
		List<Payment> payments = paymentRepo.findByPaymentStatus(PaymentStatus.REFUNDABLE)
				.stream()
				.filter(p->hasExceededRefundableDuration(p))
				.map(p -> {
					try {						
						productConfirmationFacade.confirmPayment(p,null);
					return p;
					}catch(Exception ex){
						//preventing exceptions on one element of breaking the stream
						logger.error(ex.getMessage());
						return null;
					}
				}).toList();
	}
	private boolean hasExceededRefundableDuration(Payment payment) {
		LocalDateTime creationDate = payment.getCreatedDate();
		Duration paymentAge = Duration.between(creationDate, LocalDateTime.now());
		Duration refundLimit = Duration.ofMinutes(1L);
		return paymentAge.compareTo(refundLimit) > 0;
	}
}
