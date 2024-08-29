package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Person;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController extends BaseControllerImpl<Person, Long> {

//	@Autowired
//	private InstructorService instructorService ;
//	
}
