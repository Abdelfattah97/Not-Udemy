package com.training_system.entity;

import java.util.Set;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Student extends BaseEntity<Long>  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country; 
	
	@OneToOne
	@JoinColumn(name = "user_id",nullable = false , unique = true)
	private User user; 
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
	
	@ManyToMany
	@JoinTable(
			name = "attendance",
			joinColumns = @JoinColumn(name="lesson_id"),
			inverseJoinColumns = @JoinColumn(name="student_id")
			)
	private Set<Lesson> lessons;
	
}
