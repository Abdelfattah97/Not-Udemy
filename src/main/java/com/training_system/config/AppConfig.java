package com.training_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.training_system.entity.User;
import com.training_system.service.UserService;

@Configuration
public class AppConfig implements CommandLineRunner {

  @Autowired
  UserService userService;

  @Override
  public void run(String... args) throws Exception {

    if (userService.findAll().isEmpty()) {
      User user = new User();
      user.setEmail("mail@gmail.com");
      user.setUsername("instructor");
      user.setPassword("123");
      userService.insert(user);
    }

  }

}
