package com.training_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserType {
	@Id
	private Long id;
	
	private String typeName;
}
