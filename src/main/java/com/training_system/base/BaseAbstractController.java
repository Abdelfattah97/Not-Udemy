package com.training_system.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseAbstractController<T extends BaseEntity<ID>,ID> implements BaseController<T, ID>{

	@Autowired
	BaseService<T, ID> baseService;

	@Override
	@GetMapping
	public List<T> findAll() {
		return baseService.findAll();
	}

	@Override
	@GetMapping("/{id}")
	public T findById(@PathVariable ID id) {
		return baseService.findById(id);
	}

	@Override
	@PostMapping
	public T Insert(@RequestBody T entity) {
		return baseService.insert(entity);
	}

	@Override
	@PutMapping
	public T update(@RequestBody T entity) {
		return baseService.update(entity);
	}
	
}
