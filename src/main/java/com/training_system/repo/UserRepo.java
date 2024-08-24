package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.User;

public interface UserRepo extends JpaRepository<User,Long>{

}
