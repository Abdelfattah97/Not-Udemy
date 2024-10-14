package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.enums.PasswordRecoveryMethod;
import com.training_system.service.password_reset.PasswordResetFacade;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	PasswordResetFacade passwordResetFacade;
	
	@GetMapping("/forgetPassword")
	public String recoverPassword(@RequestParam String email ) {
		return passwordResetFacade.requestPasswordReset(PasswordRecoveryMethod.EMAIL, email);
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam String token , @RequestParam String password) {
		return passwordResetFacade.changePassword(token, password);
	}
}
