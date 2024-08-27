package com.training_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Instructor;
import com.training_system.service.InstructorService;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController extends BaseControllerImpl<Instructor, Long> {

	
	@Autowired
	private InstructorService instructorService ;

	@Override
	@GetMapping
	public List<Instructor> findAll() {
		return super.findAll();
	}

	@Override
	@GetMapping("/{id}")
	public Instructor findById(@PathVariable Long id) {
		return super.findById(id);
	}

	@Override
	@PostMapping
	public Instructor Insert(@RequestBody Instructor entity) {
		return super.Insert(entity);
	}

	@Override
	@PutMapping
	public Instructor update(@RequestBody Instructor entity) {
		return super.update(entity);
	}

	
	
}
