package com.training_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.training_system.entity.User;
import com.training_system.entity.UserType;
import com.training_system.service.UserService;
import com.training_system.service.UserTypeService;

@Configuration
public class AppStratUp implements CommandLineRunner {

	@Autowired
	UserService userService;

	@Autowired
	UserTypeService userTypeService;

	@Override
	public void run(String... args) throws Exception {

		if (userTypeService.findAll().isEmpty()) {
			UserType instructor = new UserType();
			instructor.setTypeName("Instructor");
			UserType student = new UserType();
			student.setTypeName("Student");

			student = userTypeService.insert(student);
			instructor = userTypeService.insert(instructor);
		}

		if (userService.findAll().isEmpty()) {

			UserType instructor = userTypeService.findByTypeName("Instructor");
			UserType student = userTypeService.findByTypeName("Student");
			
			User user = new User();
			user.setEmail("mail@gmail.com");
			user.setUsername("instructor");
			user.setPassword("123");
			user.setUserType(instructor);
			userService.insert(user);

		}

	}


}
