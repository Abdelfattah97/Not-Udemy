package com.training_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.training_system.entity.Course;
import com.training_system.entity.Person;
import com.training_system.entity.enums.Currency;
import com.training_system.service.CourseService;
import com.training_system.service.UserService;


@Controller
public class NavigationController {

	@Autowired
	private  UserService userService;
	@Autowired
	private  CourseService courseService;
	
	@Value("${STRIPE_PUBLIC_KEY}")
	private  String stripePublicKey;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Secured(value = "student")
	@GetMapping("api/course/{courseId}/checkout")
	public String checkout(Model model, @AuthenticationPrincipal UserDetails userDetails , @PathVariable Long courseId) {
		logger.warn("UserName: "+userDetails.getUsername());
		Person person = userService.findByUserName(userDetails.getUsername()).getPerson();
		Course course = courseService.findById(courseId);
		
		model.addAttribute("amount", (int)(course.getPrice())); // in cents
		model.addAttribute("stripePublicKey", stripePublicKey);
		model.addAttribute("currency", Currency.USD);
		model.addAttribute("person", person);
		model.addAttribute("course", courseService.findById(courseId));
		logger.warn("A Checkout is Requested by Person: "+person.getName()+" with person_id: "+person.getId());
		return "checkout";
	}
	
	
}
