package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.CourseStatus;

@Repository
public interface CourseStatusRepo extends BaseRepository<CourseStatus, Long>{

}
