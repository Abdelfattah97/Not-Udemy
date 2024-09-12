package com.training_system.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseAbstractController;
import com.training_system.entity.User;
import com.training_system.entity.dto.RegistrationAdminRequest;
import com.training_system.entity.dto.RegistrationRequest;
import com.training_system.entity.dto.RegistrationResponse;
import com.training_system.service.RegistrationService;
import com.training_system.service.UserService;



@RestController
@RequestMapping("/api/user")
public class UserController extends BaseAbstractController<User, Long> {

	@Autowired
	UserService userService;
	
	@Autowired
	RegistrationService registrationService;
	
	@PutMapping("/{user_id}/add/role/{role_id}")
	@PreAuthorize("hasAuthority('give_role')")
	public User addRole(@PathVariable Long user_id, @PathVariable Long role_id) {
		return userService.addRole(user_id,role_id);
	}
	
	@PostMapping("/register")
	public RegistrationResponse register(@RequestBody RegistrationRequest registrationRequest) {
       return registrationService.register(registrationRequest);
    }
	
	@PostMapping("/register/admin")
	@PreAuthorize("hasAuthority('give_role')")
	public RegistrationResponse registerAdmin(@RequestBody RegistrationAdminRequest registrationAdminRequest) {
		return registrationService.registerAdmin(registrationAdminRequest);
	}
	
}
