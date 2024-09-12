package com.training_system.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Authority;
import com.training_system.entity.Role;
import com.training_system.exceptions.AuthoritiesMatchingException;
import com.training_system.exceptions.AuthorityAlreadyExistsException;
import com.training_system.exceptions.RoleAlreadyExistsException;
import com.training_system.exceptions.RoleNotFoundExceprion;
import com.training_system.exceptions.RolesMatchingException;
import com.training_system.repo.AuthorityRepo;
import com.training_system.repo.RoleRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService  extends BaseServiceImpl<Role, Long>{

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Override
	public Role insert(Role role) {
		if(roleRepo.existsByName(role.getName())){
			throw new RoleAlreadyExistsException(String.format("Role (\"%s\") already exists",role.getName()));
		}
		return super.insert(role);
	}
	
	
	
	public Role findByName(String roleName) {
		return roleRepo.findByName(roleName)
				.orElseThrow( ()->
				new EntityNotFoundException(String.format("No Role found with name: %s",roleName))
				);
	}
	
	public Set<Role> getExactRolesByNames(Set<String> rolesNames){
		 Set<Role> roles =roleRepo.findByNameIn(rolesNames);
		if(!(roles.size() == rolesNames.size())) {
			// Determining founded and missing roles to pass them to the exception message
			Set<String> foundedRolesNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
			for(String roleName : rolesNames) {
				if(foundedRolesNames.contains(roleName)) {
					rolesNames.remove(roleName);
				}
			}
			throw new RolesMatchingException(String.format("One or More Roles Doesn't Exist: (Found:%s, Doesn't Exist:%s)", foundedRolesNames.toString(),rolesNames.toString()));
		}
		return roles;
	}



	public Role addAuthority(Long role_id, Set<String> authorities) {
		Role role =findById(role_id);
		Set<Authority> auths = authorityService.getExactRolesByNames(authorities);
		role.addAuthority(auths);
		update(role);
		return role;
	}
	
	
	
}
