package com.training_system.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Authority;
import com.training_system.exceptions.AuthoritiesMatchingException;
import com.training_system.exceptions.AuthorityAlreadyExistsException;
import com.training_system.repo.AuthorityRepo;

@Service
public class AuthorityService extends BaseServiceImpl<Authority,Long>{

	@Autowired
	AuthorityRepo authorityRepo;
	
	public Set<Authority> getExactRolesByNames(Set<String> authoritiesNames){
		 Set<Authority> authorities =authorityRepo.findByNameIn(authoritiesNames);
		if(!(authorities.size() == authoritiesNames.size())) {
			// Determining founded and missing roles to pass them to the exception message
			Set<String> foundedRolesNames = authorities.stream().map(Authority::getName).collect(Collectors.toSet());
			for(String roleName : authoritiesNames) {
				if(foundedRolesNames.contains(roleName)) {
					authoritiesNames.remove(roleName);
				}
			}
			throw new AuthoritiesMatchingException(String.format("One or More Authorities Doesn't Exist: (Found:%s, Doesn't Exist:%s)", foundedRolesNames.toString(),authoritiesNames.toString()));
		}
		return authorities;
	}
	
	@Override
	public Authority insert(Authority auth) {
		if(authorityRepo.existsByName(auth.getName())){
			throw new AuthorityAlreadyExistsException(String.format("Authority (\"%s\") already exists",auth.getName()));
		}
		return super.insert(auth);
	}
	
	
}
