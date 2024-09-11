package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Person;
import com.training_system.service.WalletService;


@RestController
@RequestMapping("/api/wallet")
public class WalletController {

	@Autowired
	WalletService walletService;
	
	@GetMapping("/balance")
	public Double balance(@AuthenticationPrincipal UserDetails userDetails) {
		return walletService.getBalance(userDetails);
	}
	
}
