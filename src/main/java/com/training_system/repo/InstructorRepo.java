package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Instructor;

@Repository
public interface InstructorRepo extends BaseRepository<Instructor,Long>{

}
