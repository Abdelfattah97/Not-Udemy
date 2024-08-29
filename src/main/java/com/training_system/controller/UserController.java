package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.User;
import com.training_system.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/user")
public class UserController extends BaseControllerImpl<User, Long> {

	@Autowired
	UserService userService;
	
	@PutMapping("/{user_id}/add/role/{role_id}")
	public User addRole(@PathVariable Long user_id, @PathVariable Long role_id) {
		return userService.addRole(user_id,role_id);
	}
	
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
