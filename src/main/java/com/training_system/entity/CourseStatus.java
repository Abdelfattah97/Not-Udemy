package com.training_system.entity;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseStatus extends BaseEntity<Long>  {
	
	@Id
	private Long id;
	
	private String name;
	
}
