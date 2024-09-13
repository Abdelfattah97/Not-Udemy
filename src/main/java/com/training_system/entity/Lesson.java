package com.training_system.entity;

import java.util.Set;

import com.training_system.base.BaseEntity;
import com.training_system.converter.LessonTypeConverter;
import com.training_system.entity.enums.LessonType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
	
	@Column(name="title", nullable = false)
	private String title;
	
	@ManyToOne
	@JoinColumn(name="course_id",nullable = false)
	private Course course;
	
	@Column(name="file_path", nullable = true)
	private String filePath;
	
	@OneToMany(mappedBy = "quiz")
	private Set<Question> questions;
	
	@Column(name="content_type", nullable = false)
	@Convert(converter = LessonTypeConverter.class)
	private LessonType lessonType;
}
