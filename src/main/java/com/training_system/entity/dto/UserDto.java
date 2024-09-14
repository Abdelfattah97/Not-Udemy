package com.training_system.entity.dto;

import java.util.List;

import com.training_system.entity.Role;

import lombok.Data;

@Data
public class UserDto {
	private Long userId;
	private String username;
	private String email;
	private Long personId;
	private String name;
	private List<Role> roles;
	private String password;
//	private List<Role> authorities;
	
}
