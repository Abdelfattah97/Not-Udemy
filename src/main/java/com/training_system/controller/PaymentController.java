package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.dto.ChargeRequest;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.ProductType;
import com.training_system.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController extends BaseControllerImpl<Payment, Long> {
	@Autowired
	PaymentService paymentService;
	
	@PostMapping("/course/charge")
	public Enrollment charge(@RequestParam(name ="amount" ) Integer amountCents , @RequestParam String stripeToken ,
			@RequestParam String stripeTokenType , @RequestParam String stripeEmail, @RequestParam Long person_id , @RequestParam Long course_id ) {
		Person person = new Person();
		person.setId(person_id);
		Course course = new Course();
		course.setId(course_id);
		
		ChargeRequest chargeRequest =
				ChargeRequest.builder()
				.person(person)
				.product(course)
				.description("Course Purchase")
				.amount(amountCents)
				.currency(Currency.USD)
				.stripeEmail(stripeEmail)
				.stripeToken(stripeToken)
				.productType(ProductType.COURSE_ENROLLMENT_PAYMENT)
				.build();
		
		return paymentService.payCourse(chargeRequest);
		
		
	}
}
