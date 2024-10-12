package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String recipient, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("admin@notudemy.work.gd");
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		
	}
	
}
