package com.training_system.repo;

import java.util.Optional;
import java.util.Set;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Authority;

public interface AuthorityRepo extends BaseRepository<Authority,Long> {

	Optional<Authority> findByName(String name);
	Set<Authority> findByNameIn(Set<String> authoritiesNames);
	boolean existsByName(String name);
}
