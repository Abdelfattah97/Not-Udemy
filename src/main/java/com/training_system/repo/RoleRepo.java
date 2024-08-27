package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Role;

@Repository
public interface RoleRepo extends BaseRepository<Role,Long>{

}
