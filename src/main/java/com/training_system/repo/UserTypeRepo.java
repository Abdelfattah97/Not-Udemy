package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.UserType;

public interface UserTypeRepo extends JpaRepository<UserType, Long>{

}
