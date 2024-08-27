package com.training_system.entity;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentMethod extends BaseEntity<Long> {

	@Column(nullable = false,unique = true)
	String name;
	
}
