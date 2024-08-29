package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Country;
import com.training_system.entity.Role;
import com.training_system.repo.RoleRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService  extends BaseServiceImpl<Role, Long>{

	@Autowired
	RoleRepo roleRepo;
	
	public Role findByName(String roleName) {
		return roleRepo.findByName(roleName)
				.orElseThrow( ()->
				new EntityNotFoundException(String.format("No Role found with name: %s",roleName))
				);
	}
	
}
