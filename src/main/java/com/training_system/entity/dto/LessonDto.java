package com.training_system.entity.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.training_system.entity.Lesson;
import com.training_system.entity.enums.LessonType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
	private Long id;
	private String title;
	private Long course_id;
	private String filePath;
	private LessonType lessonType;
	

	public static LessonDto fromEntityToDto(Lesson lesson) {
		return	LessonDto.builder()
			.id(lesson.getId())
			.title(lesson.getTitle())
			.course_id(lesson.getCourse().getId())
			.filePath(lesson.getFilePath())
			.lessonType(lesson.getLessonType())
			.build();
	}
	
	public static Set<LessonDto> fromEntitiesToDtos(Set<Lesson> lessons){
		return lessons.stream().map(lesson -> fromEntityToDto(lesson)).collect(Collectors.toSet());
	}
}
