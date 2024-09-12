package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseAbstractController;
import com.training_system.entity.Person;

@RestController
@RequestMapping("/api/student")
public class StudentController extends BaseAbstractController<Person, Long> {

}
