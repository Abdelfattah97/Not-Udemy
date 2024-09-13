package com.training_system.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {

	private String username;
	private String email;
	private String name;
	private List<String> roles;
	private List<String> authorities;
	
}
