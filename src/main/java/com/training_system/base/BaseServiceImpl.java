package com.training_system.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseServiceImpl<T extends BaseEntity<ID>,ID> implements BaseService<T, ID> {

	@Autowired
	protected BaseRepository<T, ID> baseRepository;
	
	@Override
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	@Override
	public T findById(ID id) {
		return baseRepository.findById(id)
				.orElseThrow(()->
				 new EntityNotFoundException(
						 String.format("No entity found with the id: %s", id)));
	}

	@Override
	public T insert(T entity) {
		if(entity.getId()!=null) {
			throw new RuntimeException("Inserting new record with id is not allowed");
		}
		return baseRepository.save(entity);
	}

	@Override
	public T update(T entity) {
		if(entity.getId()==null) {
			throw new EntityNotFoundException("Trying to update existing record without prividing its id");
		}
		
		T exstEntity =baseRepository.findById(entity.getId())
				.orElseThrow(()->
				 new EntityNotFoundException(
						 String.format("Cannot Update: No entity found with the id: %s", entity.getId())));
		return baseRepository.save(entity);
	}

	@Override
	public void deleteById(ID id) {
		baseRepository.deleteById(id);
	}

	
	
}
