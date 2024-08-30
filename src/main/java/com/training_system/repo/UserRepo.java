package com.training_system.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.User;

@Repository
public interface UserRepo extends BaseRepository<User,Long>{

  Optional<User> findByUsernameOrEmail(String username, String email);

}
