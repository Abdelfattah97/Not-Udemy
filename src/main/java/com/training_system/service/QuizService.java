package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Course;
import com.training_system.entity.Question;
import com.training_system.entity.Quiz;
import com.training_system.repo.QuizRepo;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;

@Service
public class QuizService extends BaseServiceImpl<Quiz, Long>{
	@Autowired
	private QuizRepo quizRepo;
	
	public Quiz addQuiz(String quizName, Course course) {
		Quiz quiz = new Quiz();
		quiz.setQuizName(quizName);
		quiz.setCourse(course);
		quiz.setQuestions(new HashSet<Question>());
		
		return quizRepo.save(quiz);
	}
	
	public void deleteQuiz(Quiz quiz) {
		Quiz quizObj = quizRepo.findById(quiz.getId()).orElseThrow(() -> new EntityNotFoundException("Could not delete Quiz with id = " + quiz.getId() + " because it is Not Found!!!"));
		quizRepo.delete(quizObj);
	}
}
