package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Instructor;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController extends BaseControllerImpl<Instructor, Long> {

//	@Autowired
//	private InstructorService instructorService ;
//	
}
