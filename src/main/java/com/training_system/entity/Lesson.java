package com.training_system.entity;

import java.util.Set;

import com.training_system.base.BaseEntity;
import com.training_system.entity.enums.LessonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Lesson extends BaseEntity<Long>  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable = false)
	private String title;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	@Column(name="file_path", nullable = true)
	private String filePath;
	
	@OneToMany(mappedBy = "quiz")
	private Set<Question> questions;
	
	@Column(name="content_type", nullable = false)
	private LessonType lessonType;
}
