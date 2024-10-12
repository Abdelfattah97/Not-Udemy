package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.service.EmailService;


@RestController
public class MailController {

	@Autowired
	EmailService emailService;
	
	@GetMapping("/send-mail")
	public String getMethodName() {
		String msg = "This is a test email from Spring Boot";
		emailService.sendEmail("te7azed@gmail.com", "testing spring mail",msg);
		return "request submitted";
	}
	
	
}
