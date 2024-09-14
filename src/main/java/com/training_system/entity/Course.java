package com.training_system.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training_system.base.BaseEntity;
import com.training_system.base.Product;
import com.training_system.converter.CourseStatusConverter;
import com.training_system.entity.enums.CourseStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true , of={"title"})
@ToString(callSuper = true)
public class Course  extends BaseEntity<Long> implements Product{
	
	@Column(nullable = false)
	private String title;
	
	@Convert(converter = CourseStatusConverter.class)
	@Column(nullable = false)
	private CourseStatus status;
	
	/**
	 * Price in cents which means divided by 100
	 */
	@Column(nullable = false)
	private Integer price;
	
	@ManyToOne
	@JoinColumn(name="instructor_id",nullable = false)
	private Person instructor;
	
	/**
	 * list of Enrollment that represents students enrollments to a course
	 */
	@OneToMany(mappedBy = "course")
	private Set<Enrollment> enrollments;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
	private Set<Lesson> lessons;
	
	public void addLesson(Lesson lesson){
		this.lessons.add(lesson);
		lesson.setCourse(this);
	}
	
	@Transient
	public String getProductTitle() {
        return this.getTitle();
    }
	
	@Transient
	public String getProductSimpleName() {
		return "Course";
	}
	
	
}
