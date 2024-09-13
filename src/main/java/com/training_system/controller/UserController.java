package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.User;
import com.training_system.entity.dto.RegistrationAdminRequest;
import com.training_system.entity.dto.RegistrationRequest;
import com.training_system.entity.dto.RegistrationResponse;
import com.training_system.entity.dto.UserDto;
import com.training_system.entity.dto.mapper.UserDtoMapper;
import com.training_system.service.RegistrationService;
import com.training_system.service.UserService;



@RestController
@RequestMapping("/api/user")
public class UserController extends BaseControllerImpl<User, Long> {

	@Autowired
	UserService userService;
	
	@Autowired
	RegistrationService registrationService;
	@Autowired 
	UserDtoMapper userMapper;
	
	@PutMapping("/{user_id}/add/role/{role_id}")
	@PreAuthorize("hasAuthority('give_role')")
	public UserDto addRole(@PathVariable Long user_id, @PathVariable Long role_id) {
		return userMapper.toDto(userService.addRole(user_id,role_id));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('master')")
	public void deleteById(@PathVariable Long id) {
		userService.deleteById(id);
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
