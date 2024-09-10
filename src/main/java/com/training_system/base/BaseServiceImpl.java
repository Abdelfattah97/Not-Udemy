package com.training_system.base;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseServiceImpl<T extends BaseEntity<ID>,ID> implements BaseService<T, ID> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected BaseRepository<T, ID> baseRepository;
	
	/**
	 * This method's job is to throw an exception if entity is not suitable for the insertion
	 * @param entity the entity to perform a check on
	 * @throws {@link NullPointerException} if the entity is null , {@link IllegalArgumentException} if an id is passed with this entity 
	 */
	protected void insertionCheck(T entity) {
		Objects.requireNonNull(entity, "Can't insert a null object");
		if(entity.getId()!=null) {
			throw new IllegalArgumentException("Inserting new record with id is not allowed");
		}
	}
	
	@Override
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	@Override
	public T findById(ID id) {
		return baseRepository.findById(id)
				.orElseThrow(()->{
					logger.error("Entity not found at: "+this.getClass());
				 throw new EntityNotFoundException(
						 String.format("%s: No Entity found with the id: %s", this.getClass().getName(),id));
				 });
	}

	@Override
	public T insert(T entity) {
		insertionCheck(entity);
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
