package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Person;
import com.training_system.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/wallet")
public class WalletController {

	@Autowired
	WalletService walletService;
	
	@GetMapping("/person/{person_id}")
	public Double balance(@PathVariable Long person_id, @AuthenticationPrincipal UserDetails userDetails) {
		Person person = new Person();
		person.setId(person_id);
		return walletService.getBalance(person,userDetails);
	}
	
	
}
