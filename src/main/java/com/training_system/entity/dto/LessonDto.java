package com.training_system.entity.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Lesson;
import com.training_system.entity.Question;
import com.training_system.entity.enums.LessonType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
	private Long id;
	private String title;
	private Long course_id;
	private String filePath;
	private LessonType lessonType;
	

	public static LessonDto fromEntityToDto(Lesson lesson) {
		return new LessonDto(
				lesson.getId(), 
				lesson.getTitle(),
				lesson.getCourse().getId(),
				lesson.getFilePath(),
				lesson.getLessonType()
			);
	}
	
	public static Set<LessonDto> fromEntitiesToDtos(Set<Lesson> lessons){
		return lessons.stream().map(lesson -> fromEntityToDto(lesson)).collect(Collectors.toSet());
	}
}
