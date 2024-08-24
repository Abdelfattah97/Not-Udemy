package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Long>{

}
