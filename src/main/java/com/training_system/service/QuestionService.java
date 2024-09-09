package com.training_system.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Lesson;
import com.training_system.entity.Question;
import com.training_system.repo.QuestionRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuestionService extends BaseServiceImpl<Question, Long> {
	@Autowired
	private QuestionRepo questionRepo;
	
	public Set<Question> getQuizQuestions(Long quiz_id){
		return questionRepo.findByQuiz_Id(quiz_id);
	}
	
	public Question addQuestion(Lesson quiz, String content, String answerA, String answerB, String answerC, String answerD, Character correctAnswer) {
		Question question = new Question(quiz, content, answerA, answerB, answerC, answerD, correctAnswer);
		return questionRepo.save(question);
	}
	
	public void deleteQuestion(Question question) {
		Question questionObj = questionRepo.findById(question.getId()).orElseThrow(() -> new EntityNotFoundException("Could not delete question with id = " + question.getId() + " because it is Not Found!!!"));
		questionRepo.delete(questionObj);
	}
	
	@Transactional
	public Question updateQuestion(Question question, Lesson quiz, String content, String answerA, String answerB, String answerC, String answerD, Character correctAnswer) {
		question.setQuiz(quiz);
		question.setAnswerD(content);
		question.setAnswerA(answerA);
		question.setAnswerB(answerB);
		question.setAnswerC(answerC);
		question.setAnswerD(answerD);
		question.setCorrectAnswer(correctAnswer);
		
		return questionRepo.save(question);
	}
}
