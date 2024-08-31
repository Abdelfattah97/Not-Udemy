package com.training_system.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Person;
import com.training_system.entity.Role;

@Repository
public interface PersonRepo extends BaseRepository<Person,Long>{

	Optional<Person> findByName(String name);

}
