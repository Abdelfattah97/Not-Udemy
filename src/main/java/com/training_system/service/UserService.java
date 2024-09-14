package com.training_system.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Authority;
import com.training_system.entity.Person;
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
	private PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	RoleService roleService;

	public boolean isInstructor(User user) {
		Role instructor = roleService.findByName("instructor");

		return (user != null && user.getRoles().contains(instructor));
	}

	@PreAuthorize("hasAuthority('master')")
	public User addRole(Long user_id, Long role_id) {

		User user = findById(user_id);
		Role role = roleService.findById(role_id);

		user.addRoles(role);
		return super.update(user);
	}

	@Override
	public User insert(User entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return super.insert(entity);
	}
	
	@Override
	public User update(User user) {
		if(user.getId()==null) {
			throw new EntityNotFoundException("Trying to update existing record(User) without providing its id");
		}
		
		User existUser =userRepo.findById(user.getId())
				.orElseThrow(()->
				 new EntityNotFoundException(
						 String.format("Cannot Update: No User found with the id: %s", user.getId())));
	
		existUser.setEmail(user.getEmail());
		existUser.setPassword(passwordEncoder.encode(user.getPassword()));
		existUser.addRoles(user.getRoles());
		existUser.getPerson().setName(user.getPerson().getName());
		
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsernameOrEmail(username, username).map(user -> {
			Set<Role> userRoles = user.getRoles();

			Set<String> authorities = userRoles.stream().flatMap(r -> r.getAuthorities().stream())
					.map(a -> a.getAuthority()).collect(Collectors.toSet());

			authorities.addAll(userRoles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
			
			String[] rolesAndAuthorities = authorities.toArray(new String[0]);

			return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
					.password(user.getPassword()).authorities(rolesAndAuthorities).build();
		}).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
	}

	public User findByUserName(String userName) {
		return userRepo.findByUsername(userName).orElseThrow(() -> new EntityNotFoundException("UserName Not Found!"));
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userRepo.findByUsername(userDetails.getUsername())
					.orElseThrow(() -> new RuntimeException("The Authenticated User is not found id database"));
		}
		throw new RuntimeException("No user is currently authenticated");
	}

	public boolean isCurrentUser(User user) {
		return getCurrentUser().getId().equals(user.getId());
	}

	public boolean isCurrentUser(Person person) {
		return getCurrentUser().getPerson().getId().equals(person.getId());
	}

}
