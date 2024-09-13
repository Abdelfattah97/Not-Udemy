package com.training_system.entity.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.training_system.entity.Course;
import com.training_system.entity.enums.CourseStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseDto {
	private Long id;
	private String title;
	private CourseStatus status;
	private Integer price;
	private Long instructorId;
	private String instructorName;
	private Set<LessonDto> lessons;
	
	
	public static CourseDto fromEntityToDto(Course course) {
	return CourseDto.builder()
			.id(course.getId())
			.title(course.getTitle())
			.status(course.getStatus())
			.price(course.getPrice())
			.instructorId(course.getInstructor().getId())
			.instructorName(course.getInstructor().getName())
			.build();
	}
	
	public static Set<CourseDto> fromEntitiesToDto(Set<Course> courses){
		return courses.stream().map(course -> fromEntityToDto(course)).collect(Collectors.toSet());
	}
}
