package com.training_system.entity.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.training_system.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
	private Long id;
	private Long quiz_id;
	private String content;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private Character correctAnswer;
	
	public static QuestionDto fromEntityToDto(Question question) {
		return new QuestionDto(
				question.getId(),
				question.getQuiz().getId(),
				question.getContent(),
				question.getAnswerA(),
				question.getAnswerB(),
				question.getAnswerC(),
				question.getAnswerD(),
				question.getCorrectAnswer()
			);
	}
	
	public static Set<QuestionDto> fromEntitiesToDtos(Set<Question> questions){
		return questions.stream().map(question -> fromEntityToDto(question)).collect(Collectors.toSet());
	}
}
