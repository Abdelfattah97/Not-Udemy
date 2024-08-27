package com.training_system.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseControllerImpl<T extends BaseEntity<ID>,ID> implements BaseController<T, ID>{

	@Autowired
	BaseService<T, ID> baseService;

	@Override
	public List<T> findAll() {
		return baseService.findAll();
	}

	@Override
	public T findById(ID id) {
		return baseService.findById(id);
	}

	@Override
	public T Insert(T entity) {
		return baseService.insert(entity);
	}

	@Override
	public T update(T entity) {
		return baseService.update(entity);
	}
	
}
