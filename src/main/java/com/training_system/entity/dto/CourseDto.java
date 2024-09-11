package com.training_system.entity.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.training_system.entity.Course;
import com.training_system.entity.enums.CourseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
	private Long id;
	private String title;
	private CourseStatus status;
	private Integer price;
	private Long instructor_id;
	
	public static CourseDto fromEntityToDto(Course course) {
		return new CourseDto(course.getId(), course.getTitle(), course.getStatus(), course.getPrice(), course.getInstructor().getId());
	}
	
	public static Set<CourseDto> fromEntitiesToDto(Set<Course> courses){
		return courses.stream().map(course -> fromEntityToDto(course)).collect(Collectors.toSet());
	}
}
