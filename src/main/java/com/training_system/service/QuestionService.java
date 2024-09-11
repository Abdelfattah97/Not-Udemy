package com.training_system.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Lesson;
import com.training_system.entity.Question;
import com.training_system.entity.dto.QuestionDto;
import com.training_system.repo.LessonRepo;
import com.training_system.repo.QuestionRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuestionService extends BaseServiceImpl<Question, Long> {
	@Autowired
	private QuestionRepo questionRepo;
	
	@Autowired
	private LessonRepo lessonRepo;
	
	public Set<QuestionDto> getQuizQuestions(Long quiz_id){
		return QuestionDto.fromEntitiesToDtos(questionRepo.findByQuiz_Id(quiz_id)); 
	}
	
	public Question addQuestion(QuestionDto questionDto) {
		
		Lesson quiz = lessonRepo.findById(questionDto.getQuiz_id()).orElseThrow(() -> new EntityNotFoundException("There is no quiz with this id"));
		
		Question question = new Question(quiz, questionDto.getContent(), questionDto.getAnswerA(), questionDto.getAnswerB(), questionDto.getAnswerC(), questionDto.getAnswerD(), questionDto.getCorrectAnswer());
		return questionRepo.save(question);
	}
	
	public void deleteQuestion(Long question_id) {
		Question question = questionRepo.findById(question_id).orElseThrow(() -> new EntityNotFoundException("Could not delete question with id = " + question_id + " because it is Not Found!!!"));
		questionRepo.delete(question);
	}
	
	@Transactional
	public Question updateQuestion(QuestionDto questionDto) {
		Lesson quiz = lessonRepo.findById(questionDto.getQuiz_id()).orElseThrow(() -> new EntityNotFoundException("There is no quiz with this id"));
		
		Question question = new Question(quiz, questionDto.getContent(), questionDto.getAnswerA(), questionDto.getAnswerB(), questionDto.getAnswerC(), questionDto.getAnswerD(), questionDto.getCorrectAnswer());
		
		return update(question);
	}
}
