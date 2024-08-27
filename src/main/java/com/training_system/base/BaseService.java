package com.training_system.base;

import java.util.List;

public interface BaseService<T extends BaseEntity<ID>,ID> {
	
	List<T> findAll();

	T findById(ID id);

	T insert(T entity);

	T update(T entity);
	
	void deleteById(ID id);

}
