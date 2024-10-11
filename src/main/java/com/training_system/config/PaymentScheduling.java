package com.training_system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.training_system.service.PaymentService;
import com.training_system.service.RefundExpirationManager;

@Component
public class PaymentScheduling {

	@Autowired
	RefundExpirationManager refundExpirationManager;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Scheduled(cron = "0 * * * * ?")
	public void everyMinCheck() {
		logger.info("Cron job Started");
//		refundExpirationManager.limitExpiredRefundables();
		logger.info("Cron job Ended");
	}
	
//	@Scheduled(cron = "0 0 0 * * ?")
//	public void everyDayCheck() {
//		logger.info("Cron job Started");
//		paymentService.limitExpiredRefundables();
//		logger.info("Cron job Ended");
//	}
	
}
