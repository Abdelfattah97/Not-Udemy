package com.training_system.entity.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training_system.entity.Course;
import com.training_system.entity.dto.CourseDto;

@Mapper(componentModel = "spring",uses = LessonDtoMapper.class)
public interface CourseDtoMapper {

	@Mapping(source = "instructor.id", target = "instructorId")
	@Mapping(source = "instructor.name", target = "instructorName")
	@Mapping(source = "id", target = "courseId")
	@Mapping(source = "lessons", target = "lessons", qualifiedBy = {PublicMapping.class})
	@PublicMapping
	public CourseDto toDtoPublic(Course course);

	@Mapping(source = "instructor.id", target = "instructorId")
	@Mapping(source = "instructor.name", target = "instructorName")
	@Mapping(source = "id", target = "courseId")
	@Mapping(source = "lessons", target = "lessons", qualifiedBy = { DetailedMapping.class })
	@DetailedMapping
	public CourseDto toDtoDetailed(Course course);
	
	
	@Mapping(source = "instructorId", target = "instructor.id")
	@Mapping(source = "courseId", target = "id")
	public Course toEntityCourse(CourseDto dto);

	@PublicMapping
	default List<CourseDto> toDtoPublic(List<Course> courses) {
		return courses.stream().map(this::toDtoPublic).collect(Collectors.toList());
	}

	@DetailedMapping
	default List<CourseDto> toDtoDetailed(List<Course> courses) {
		return courses.stream().map(this::toDtoDetailed).collect(Collectors.toList());
	}
}
