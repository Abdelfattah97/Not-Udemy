package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseControllerImpl<User, Long> {

//	@Override
//	@GetMapping
//	public List<User> findAll() {
//		return super.findAll();
//	}
//
//	@Override
//	@GetMapping("/{id}")
//	public User findById(@PathVariable Long id) {
//		return super.findById(id);
//	}
//
//	@Override
//	@PostMapping
//	public User Insert(@RequestBody User entity) {
//		return super.Insert(entity);
//	}
//
//	@Override
//	@PutMapping
//	public User update(@RequestBody User entity) {
//		return super.update(entity);
//	}

	
	
}
