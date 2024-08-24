package com.training_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import com.training_system.entity.Status;

@Entity
@Data
public class Course {

	private Long id;
	
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	private double price;
	
}
