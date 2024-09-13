package com.training_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
//	@PreAuthorize("permitAll()")
//	@PermitAll
	@GetMapping("/api/test")
	public String test() {
		return "Anonymous";
	}
}
