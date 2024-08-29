package com.training_system.entity;

import java.time.LocalDate;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "course_student",
uniqueConstraints = 
	    @UniqueConstraint(columnNames = {"person_id", "course_id"})
)
@Data
public class Enrollment extends BaseEntity<Long> {

	@ManyToOne
	@JoinColumn(name="student_id",nullable = false)
	private Person student;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	@OneToOne
	@Column(nullable = false,unique = true)
	private Payment payment;
	
	@Column(name="enrollment_date")
	private LocalDate enrollmentDate;
	
	private boolean isConfirmed;
	
}
