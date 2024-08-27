package com.training_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.service.UserService;
//import com.training_system.service.RoleService;

@Configuration
public class AppStratUp implements CommandLineRunner {

	@Autowired
	UserService userService;

//	@Autowired
//	RoleService userTypeService;

	@Override
	public void run(String... args) throws Exception {

//		if (RoleService.findAll().isEmpty()) {
//			Role instructor = new Role();
//			instructor.setName("Instructor");
//			Role student = new Role();
//			student.setName("Student");
//
//			student = RoleService.insert(student);
//			instructor = RoleService.insert(instructor);
//		}

		if (userService.findAll().isEmpty()) {
			User user = new User();
			user.setEmail("mail@gmail.com");
			user.setUsername("instructor");
			user.setPassword("123");
			userService.insert(user);
		}

	}


}
