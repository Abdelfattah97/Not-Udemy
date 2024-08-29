package com.training_system.entity;

import java.util.Set;

import com.training_system.base.BaseEntity;

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
public class Course  extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private CourseStatus status;
	
	@Column(nullable = false)
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="instructor_id",nullable = false)
	private Person instructor;
	
	/**
	 * list of Enrollment that represents students enrollments to a course
	 */
	@OneToMany(mappedBy = "course")
	private Set<Enrollment> enrollments;
	
	@OneToMany(mappedBy = "course")
	private Set<Lesson> lessons;
	
	public void addLesson(Lesson lesson){
		this.lessons.add(lesson);
		lesson.setCourse(this);
	}
	
}
