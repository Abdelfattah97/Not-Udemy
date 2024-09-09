//package com.training_system.entity;
//
//import java.util.Set;
//
//import com.training_system.base.BaseEntity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Quiz extends BaseEntity<Long> {
//	@ManyToOne
//	@JoinColumn(name="course_id",nullable = false)
//	private Course course;
//	
//	@Column(name="content",nullable = false)
//	private String quizName;
//	
//	@OneToMany(mappedBy = "quiz")
//	private Set<Question> questions;
//}
