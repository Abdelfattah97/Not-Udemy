package com.training_system.entity.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training_system.entity.Lesson;
import com.training_system.entity.dto.LessonDto;

@Mapper(componentModel = "spring")
public interface LessonDtoMapper {

	@Mapping(source = "course.id", target = "course_id")
	@Mapping(source = "filePath", target = "filePath", ignore = true)
	@PublicMapping
	public LessonDto toDtoPublic(Lesson lesson);
	
	@Mapping(source = "course.id", target = "course_id")
	@DetailedMapping
	public LessonDto toDtoDetailed(Lesson lesson);

	
	@PublicMapping
	default List<LessonDto> toDtoPublic(List<Lesson> lessons) {
		return lessons.stream().map(this::toDtoPublic).collect(Collectors.toList());
	}

	@PublicMapping
	default Set<LessonDto> toDtoPublic(Set<Lesson> lessons) {
		return lessons.stream().map(this::toDtoPublic).collect(Collectors.toSet());
	}

	@DetailedMapping
	default List<LessonDto> toDtoDetailed(List<Lesson> lessons) {
		return lessons.stream().map(this::toDtoDetailed).collect(Collectors.toList());
	}

	@DetailedMapping
	default Set<LessonDto> toDtoDetailed(Set<Lesson> lessons) {
		return lessons.stream().map(this::toDtoDetailed).collect(Collectors.toSet());
	}

}
