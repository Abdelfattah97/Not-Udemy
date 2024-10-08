package com.training_system.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Role;

@Repository
public interface RoleRepo extends BaseRepository<Role,Long>{

	Optional<Role> findByName(String name);
	Set<Role> findByNameIn(Set<String> roleNames);
	boolean existsByName(String name);
	

}
