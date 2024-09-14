package com.training_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Authority;
import com.training_system.service.AuthorityService;

@RestController
@RequestMapping("/api/authority")
@PreAuthorize("hasRole('privilege_manager')")
public class AuthorityController {

	@Autowired
	AuthorityService authorityService;
	
	@GetMapping
	public List<Authority> findAll() {
		return authorityService.findAll();
	}

	
	@GetMapping("/{id}")
	public Authority findById(@PathVariable Long id) {
		return authorityService.findById(id);
	}

	
	@PostMapping
	public Authority Insert(@RequestBody Authority entity) {
		return authorityService.insert(entity);
	}

	
	@PutMapping
	public Authority update(@RequestBody Authority entity) {
		return authorityService.update(entity);
	}
}
