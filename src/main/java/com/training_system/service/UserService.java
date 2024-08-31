package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.repo.RoleRepo;
import com.training_system.repo.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService extends BaseServiceImpl<User, Long> implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	RoleService roleService;

	public boolean isInstructor(User user) {
		Role instructor = roleService.findByName("instructor");

		return (user != null && user.getRoles().contains(instructor));
	}

	public User addRole(Long user_id, Long role_id) {

		return null;// placeholder

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsernameOrEmail(username, username)
				.map(user -> org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
						.password(user.getPassword()).authorities(user.getRoles()).build())
				.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
	}

	public User findByUserName(String userName) {
		return userRepo.findByUsername(userName).orElseThrow(() ->
			new EntityNotFoundException("UserName Not Found!"));
	}

}
