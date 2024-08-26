package com.training_system.entity;

import java.util.Set;

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

@Entity
@Data
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country; 
	
	// I am not sure about the relation !! 
	@OneToOne
	@JoinColumn(name = "user_id")
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
