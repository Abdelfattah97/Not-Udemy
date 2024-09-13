package com.training_system.entity.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training_system.entity.Lesson;
import com.training_system.entity.dto.LessonDto;

@Mapper(componentModel="spring")
public interface LessonDtoMapper {



	
	public LessonDto toDtoSimple(Lesson lesson) ;
	
	default List<LessonDto> toListDto(List<Lesson> lessons){
		return lessons.stream().map(this::toDtoSimple).collect(Collectors.toList());
	}
	
	

}
