package com.training_system.entity;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity<Long> {

	@ManyToOne
	@JoinColumn(nullable = false)
	private PaymentMethod payMethod;
	
	@Column(nullable = false)
	private Double payAmount;
	
}
