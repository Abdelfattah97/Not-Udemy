package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.UserType;

@Repository
public interface UserTypeRepo extends BaseRepository<UserType, Long>{

	UserType findByTypeName(String typeName);
	
}
