package com.training_system.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.training_system.base.BaseEntity;
import com.training_system.converter.CurrencyConverter;
import com.training_system.converter.PaymentMethodConverter;
import com.training_system.converter.PaymentStatusConverter;
import com.training_system.converter.ProductTypeConverter;
import com.training_system.entity.enums.Currency;
import com.training_system.entity.enums.PaymentMethod;
import com.training_system.entity.enums.PaymentStatus;
import com.training_system.entity.enums.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
		uniqueConstraints = 
	    @UniqueConstraint(columnNames = {"transaction_id", "buyer_id"}))
public class Payment extends BaseEntity<Long> {

	@Convert(converter = PaymentMethodConverter.class)
	private PaymentMethod payMethod;

	/**
	 * Amount in cents which means divided by 100
	 */
	@Column(nullable = false)
	private Integer payAmount;
	
	@Convert(converter = CurrencyConverter.class)
	@Column(nullable = false)
	private Currency currency;

	@Convert(converter = ProductTypeConverter.class)
	private ProductType productType;
	
	@Column(unique = true, nullable = false)
	private String transactionId;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDate;
	
	@Convert(converter =PaymentStatusConverter.class)
	@Column(nullable = false)
	private PaymentStatus paymentStatus;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id",nullable = false)
	@JsonIncludeProperties(value = {"id","name"})
	private Person buyer;
	
}
