package com.training_system.entity.dto;

import java.util.HashSet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationAdminRequest extends RegistrationRequest {

	HashSet<String> roles;
	
}
