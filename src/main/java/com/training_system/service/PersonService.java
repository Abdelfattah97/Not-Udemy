package com.training_system.service;

import com.training_system.entity.Lesson;
import com.training_system.entity.User;
import com.training_system.entity.dto.LessonDto;
import com.training_system.entity.dto.PersonDto;
import com.training_system.repo.PersonRepo;
import com.training_system.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Person;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService  extends BaseServiceImpl<Person, Long>{
	@Autowired
    private PersonRepo personRepo;

    @Autowired
    private UserRepo userRepo;

    public Set<PersonDto> getStudents(){
        User user = userRepo.findById()
    }
}
