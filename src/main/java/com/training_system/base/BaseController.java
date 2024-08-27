package com.training_system.base;

import java.util.List;

public interface BaseController<T,ID> {
	
	List<T> findAll();
	
	T findById(ID id);
	
	T Insert(T entity);
	
	T update(T entity);
	
}
