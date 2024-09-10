package com.training_system.entity;

import java.util.Set;

import com.training_system.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Person extends BaseEntity<Long> {

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToOne
	@JoinColumn(name = "user_id",nullable = false,unique = true)
	private User user;

	@OneToMany(mappedBy = "instructor")
	private Set<Course> taughtCourses;
	
	
	@OneToMany(mappedBy = "student") 
	private Set<Enrollment> enrollments;
	
	@ManyToMany
	@JoinTable(
			name = "attendance",
			joinColumns = @JoinColumn(name="lesson_id"),
			inverseJoinColumns = @JoinColumn(name="student_id")
			)
	private Set<Lesson> attendedLessons;
	
	public void addAttendedLesson(Lesson lesson) {
		attendedLessons.add(lesson);
	}
	
//	@PrePersist
//	private void convertUserTypeToDBType(){
//		userTypes.stream().forEach((t)->userTypeValues.add(t.getValue()));
//	}
//	@PostLoad
//	private void convertDBTypeToUserType(){
//		userTypeValues.stream().forEach((t)->userTypes.add(UserType.fromValue(t)));
//	}
	
	
}
