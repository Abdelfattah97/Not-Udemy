package com.training_system.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Question;
import com.training_system.entity.dto.QuestionDto;
import com.training_system.service.QuestionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/question")
public class QuestionController extends BaseControllerImpl<Question, Long>{
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/{quiz_id}/getquestions")
	public Set<QuestionDto> getQuestions(@PathVariable(name = "quiz_id") Long quizId){
		return questionService.getQuizQuestions(quizId);
	}
	
	@PostMapping("/addquestion")
	public void setQuestion(@RequestBody QuestionDto questionDto) {
		questionService.addQuestion(questionDto);
	}
	
	@DeleteMapping("/deletequestion/{question_id}")
	public void deleteQuestion(@PathVariable(name = "question_id") Long question_id) {
		questionService.deleteQuestion(question_id);
	}
	
	@PutMapping("/updatequestion")
	public void updateQuestion(@RequestBody QuestionDto questionDto) {
		questionService.updateQuestion(questionDto);
	}
}
