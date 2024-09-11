package com.training_system.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training_system.base.BaseEntity;
import com.training_system.base.Product;
import com.training_system.base.ProductTransaction;
import com.training_system.converter.EnrollmentStatusConverter;
import com.training_system.entity.enums.EnrollmentStatus;
import com.training_system.entity.enums.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_student",
uniqueConstraints = 
	    @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Enrollment extends BaseEntity<Long> implements ProductTransaction {	

	@ManyToOne
	@JoinColumn(name="student_id",nullable = false)
	private Person student;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	@OneToOne
	@JoinColumn(name="pay_id",nullable = false,unique = true)
	private Payment payment;
	
	@Column(name="enrollment_date")
	private LocalDate enrollmentDate;
	
	@Column(name="enrollment_status")
	@Convert(converter = EnrollmentStatusConverter.class)
	private EnrollmentStatus enrollment_status;

	@Override
	@Transient
	@JsonIgnore
	public Person getBuyer() {
	return this.student;
	}

	@Override
	@Transient
	@JsonIgnore
	public Product getProduct() {
		return this.course;
	}

	@Override
	public LocalDate getTransactiontDate() {
		return this.enrollmentDate;
	}

	@Override
	@Transient
//	@JsonIgnore
	public ProductType getProductType() {
		return ProductType.COURSE_ENROLLMENT;
	}
	
}
