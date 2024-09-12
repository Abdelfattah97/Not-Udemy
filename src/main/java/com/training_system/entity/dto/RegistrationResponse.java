package com.training_system.entity.dto;

import java.util.Set;

import com.training_system.entity.Role;

import lombok.Data;

@Data
public class RegistrationResponse {

	private Long userId;
	
	private Long personId;
	
	private String username;
	
	private String name;
	
	private Set<String> roles;
	
}
