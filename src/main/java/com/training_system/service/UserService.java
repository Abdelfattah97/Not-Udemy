package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.repo.RoleRepo;
import com.training_system.repo.UserRepo;

@Service
public class UserService extends BaseServiceImpl<User, Long> {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	RoleService roleService;
	
	public boolean isInstructor(User user){	
		Role instructor = roleService.findByName("instructor");
		
		return ( user!=null && user.getRoles().contains(instructor) );
	}

	public User addRole(Long user_id, Long role_id) {

		return null;//placeholder
		
	}

}
