package com.training_system.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.training_system.entity.Person;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.entity.dto.RegistrationAdminRequest;
import com.training_system.entity.dto.RegistrationRequest;
import com.training_system.entity.dto.RegistrationResponse;
import com.training_system.entity.dto.mapper.RegistrationMapper;
import com.training_system.exceptions.NoRoleRegistrationException;

import jakarta.transaction.Transactional;

@Component
public class RegistrationServiceImpl implements RegistrationService{

	@Autowired
	UserService userService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
    RoleService roleService;
	
	@Autowired
    RegistrationMapper registrationMapper;
	
	@Override
	@Transactional
	public RegistrationResponse register(RegistrationRequest registerationRequest) {
		
		User newUser = registrationMapper.toUserEntity(registerationRequest);
		
		Person newPerson = registrationMapper.toPersonEntity(registerationRequest);
		
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleService.findByName("student"));
		roles.add(roleService.findByName("instructor"));
		
		newUser.setRoles(roles);
		newPerson.setUser(newUser);
		newUser.setPerson(newPerson);
		
		newUser =userService.insert(newUser);
		newPerson=newUser.getPerson();
		
		return registrationMapper.toResponseDto(newPerson, newUser);
	}

	@Override
	public RegistrationResponse registerAdmin(RegistrationAdminRequest registrationAdminRequest) {
		
		if(registrationAdminRequest.getRoles().isEmpty()) {
			throw new NoRoleRegistrationException("Must define atleast one valid role to register!");
		}
		User newUser = registrationMapper.toUserEntity(registrationAdminRequest);
		
		Person newPerson = registrationMapper.toPersonEntity(registrationAdminRequest);
		
			
		Set<Role> roles = roleService.getExactRolesByNames(registrationAdminRequest.getRoles());
		
		newUser.setRoles(roles);
		newPerson.setUser(newUser);
		newUser.setPerson(newPerson);
		
		newUser =userService.insert(newUser);
		newPerson=newUser.getPerson();
		
		return registrationMapper.toResponseDto(newPerson, newUser);
		
	}
	
	
}
