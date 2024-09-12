package com.training_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.training_system.base.Product;
import com.training_system.entity.Course;
import com.training_system.entity.Person;
import com.training_system.entity.dto.CheckoutRequest;
import com.training_system.entity.dto.CheckoutResponse;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.ProductType;
import com.training_system.service.CourseService;
import com.training_system.service.PurchaseFacade;
import com.training_system.service.UserService;

@Controller
public class NavigationController {

	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PurchaseFacade purchaseFacade;

	@Value("${STRIPE_PUBLIC_KEY}")
	private String stripePublicKey;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PreAuthorize("hasAuthority('enroll_course')")
	@GetMapping("api/payment/{productTypename}/{productId}/checkout/ui")
	public String checkout(Model model, @AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long productId ,@PathVariable String productTypename ) {
		ProductType productType = ProductType.valueOf(productTypename.toUpperCase());
		CheckoutResponse checkoutResponse = purchaseFacade.checkout(userDetails, 
				CheckoutRequest.builder().productId(productId).productType(productType).productId(productId).build());
		
		Person person = new Person();
		person.setId(userService.findByUserName(userDetails.getUsername()).getPerson().getId());
		Product product = checkoutResponse.getProduct();
		

		model.addAttribute("amount", (int) (product.getPrice())); // in cents
		model.addAttribute("stripePublicKey", checkoutResponse.getProviderPublicKey());
		model.addAttribute("currency", checkoutResponse.getCurrency());
		model.addAttribute("person", person);
		model.addAttribute("product", product);
		model.addAttribute("productType",productType);
		
		return "checkout";
	}

}
