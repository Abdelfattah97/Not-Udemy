package com.training_system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.training_system.entity.Country;
import com.training_system.entity.Course;
import com.training_system.entity.Person;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.entity.enums.CourseStatus;
import com.training_system.repo.CountryRepo;
import com.training_system.repo.PersonRepo;
import com.training_system.repo.RoleRepo;
import com.training_system.repo.UserRepo;
import com.training_system.service.CourseService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

	private final UserRepo userRepo;

	private final RoleRepo roleRepo;

	private final PersonRepo personRepo;

	private final CourseService courseService;

	private final CountryRepo countryRepo;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
		insertInstructorAndCourse(instructor);
		insertStudent(student);
	}

	void insertStudent(User user) {
		Country egypt = countryRepo.findByCountryName("Egypt").orElseGet(() -> {
			Country country = new Country();
			country.setCountryName("Egypt");
			return countryRepo.save(country);
		});

		Person student = personRepo.findByName("A Student").orElseGet(() -> {
			Person person = new Person();
			person.setUser(user);
			person.setCountry(egypt);
			person.setName("A Student");
			return personRepo.save(person);
		});

	}

	void insertInstructorAndCourse(User user) {

		Country egypt = countryRepo.findByCountryName("Egypt").orElseGet(() -> {
			Country country = new Country();
			country.setCountryName("Egypt");
			return countryRepo.save(country);
		});

		Person instructor = personRepo.findByName("MR PROf").orElseGet(() -> {
			Person person = new Person();
			person.setUser(user);
			person.setCountry(egypt);
			person.setName("MR PROf");
			return personRepo.save(person);
		});

		insertCourse(instructor);

	}

	void insertCourse(Person instructor) {
		logger.warn("insertCourse Method start:");
		if (courseService.findAll().isEmpty()) {
			logger.warn("empty courses table , will insert new one!");
			Course course = new Course();
			course.setInstructor(instructor);
			course.setTitle("JAVA INTRODUCTION");
			course.setPrice(10000);
			course.setStatus(CourseStatus.PUBLIC);
			course = courseService.insert(course);
			logger.warn(course.getTitle());
		} else {
			logger.warn("Found Courses in DB");
		}

	}

}
