package com.training_system.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.training_system.base.BaseEntity;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.PaymentMethod;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder( )
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "wallet")
public class WalletTransaction extends BaseEntity<Long> {

	@Column(nullable = false)
	private Integer transactionAmount;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	@JsonIncludeProperties(value = {"id","name"})
	private Person person;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDate;
	
}
