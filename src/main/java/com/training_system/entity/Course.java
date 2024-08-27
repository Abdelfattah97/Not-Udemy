package com.training_system.entity;

import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import com.training_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder.Default;
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
	private Instructor instructor;
	
	@ManyToMany
	@JoinTable(
			name = "course_student",
			joinColumns = @JoinColumn(name="course_id"),
			inverseJoinColumns = @JoinColumn(name="student_id")
			)
	private Set<Student> students;
	
	@OneToMany(mappedBy = "course")
	private Set<Lesson> lessons;
	
	public void addLesson(Lesson lesson){
		this.lessons.add(lesson);
		lesson.setCourse(this);
	}
	
}
