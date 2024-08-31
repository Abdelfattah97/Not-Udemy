package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Question;

@Repository
public interface QuestionRepo extends BaseRepository<Question, Long>{
	
}
