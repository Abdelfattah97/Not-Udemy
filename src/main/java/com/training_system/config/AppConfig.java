package com.training_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.repo.RoleRepo;
import com.training_system.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

  private final UserRepo userRepo;

  private final RoleRepo roleRepo;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    Role instructorRole = roleRepo.findByName("instructor").orElseGet(() -> {
      Role role = new Role();
      role.setName("instructor");
      return roleRepo.save(role);
    });

    Role studentRole = roleRepo.findByName("student").orElseGet(() -> {
      Role role = new Role();
      role.setName("student");
      return roleRepo.save(role);
    });

    User student = new User();
    student.setId(1L);
    student.setUsername("student");
    student.setEmail("student@gmail.com");
    student.setPassword(passwordEncoder.encode("password"));
    student.addRoles(studentRole);
    userRepo.save(student);

    User instructor = new User();
    instructor.setId(2L);
    instructor.setUsername("instructor");
    instructor.setEmail("instructor@gmail.com");
    instructor.setPassword(passwordEncoder.encode("password"));
    instructor.addRoles(instructorRole);
    userRepo.save(instructor);

    User master = new User();
    master.setId(3L);
    master.setUsername("master");
    master.setEmail("master@gmail.com");
    master.setPassword(passwordEncoder.encode("password"));
    master.addRoles(studentRole, instructorRole);
    userRepo.save(master);
  }

}
