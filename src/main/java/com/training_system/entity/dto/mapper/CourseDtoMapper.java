package com.training_system.entity.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training_system.entity.Course;
import com.training_system.entity.dto.CourseDto;

@Mapper(componentModel="spring",uses = LessonDtoMapper.class)
public interface CourseDtoMapper {


	@Mapping(source="instructor.id",target="instructorId")
	@Mapping(source="instructor.name",target="instructorName")
	@Mapping(source = "lessons",target="lessons",ignore = true)
	public CourseDto toDtoSimple(Course course) ;
	
	@Mapping(source="instructor.id",target="instructorId")
	@Mapping(source="instructor.name",target="instructorName")
	public CourseDto toDtoDetailed(Course course) ;
	
	@Mapping(source="instructorId", target="instructor.id")
	public Course toEntityCourse(CourseDto dto);
	
	default List<CourseDto> toDtoSimple(List<Course> courses) {
		return courses.stream().map(this::toDtoSimple).collect(Collectors.toList());
	}
	default List<CourseDto> toDtoDetailed(List<Course> courses) {
		return courses.stream().map(this::toDtoDetailed).collect(Collectors.toList());
	}

}
