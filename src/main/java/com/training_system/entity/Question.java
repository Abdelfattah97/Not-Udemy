package com.training_system.entity;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity<Long>{
	@ManyToOne
	@JoinColumn(name="quiz_id",nullable = false)
	private Quiz quiz;
	
	@Column(name="content",nullable = false)
	private String content;
	
	@Column(name="answer_a",nullable = false)
	private String answerA;
	
	@Column(name="answer_b",nullable = false)
	private String answerB;
	
	@Column(name="answer_c",nullable = false)
	private String answerC;
	
	@Column(name="answer_d",nullable = false)
	private String answerD;
	
	@Column(name="correct_answer",nullable = false)
	private Character correctAnswer;
	
}
