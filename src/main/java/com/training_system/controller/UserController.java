package com.training_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RegistrationService registrationService;
	@Autowired
	UserDtoMapper userMapper;

	@GetMapping
	@PreAuthorize("hasRole('master')")
	public List<UserDto> findAll() {
		return userMapper.toDto(userService.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('master') or #id==@userService.getCurrentUser().getId()")
	public UserDto findById(@PathVariable Long id) {
		return userMapper.toDto(userService.findById(id));
	}

	@PostMapping	
	@PreAuthorize("hasRole('master')")
	public UserDto Insert(@RequestBody User entity) {
		return userMapper.toDto(userService.insert(entity));
	}

	@PutMapping
	@PreAuthorize("hasRole('master') or #id==@userService.getCurrentUser().getId()")
	public UserDto update(@RequestBody User entity) {
		return userMapper.toDto(userService.update(entity));
	}

	@PutMapping("/{user_id}/add/role/{role_id}")
	@PreAuthorize("hasAuthority('give_role')")
	public UserDto addRole(@PathVariable Long user_id, @PathVariable Long role_id) {
		return userMapper.toDto(userService.addRole(user_id, role_id));
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
