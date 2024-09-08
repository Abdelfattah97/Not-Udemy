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
		
		Role admineRole = roleRepo.findByName("master").orElseGet(() -> {
			Role role = new Role();
			role.setName("master");
			return roleRepo.save(role);
		});

		User student = userRepo.findByUsername("student").orElseGet(() -> {
			logger.warn("This is student supplier!");
			User std = new User();
			std.setUsername("student");
			std.setEmail("student@gmail.com");
			std.setPassword(passwordEncoder.encode("password"));
			std.addRoles(studentRole);
			return userRepo.save(std);
		});

		User student2 = userRepo.findByUsername("student2").orElseGet(() -> {
			logger.warn("This is student2 supplier!");
			User std2 = new User();
			std2.setUsername("student2");
			std2.setEmail("student2@gmail.com");
			std2.setPassword(passwordEncoder.encode("password"));
			std2.addRoles(studentRole);
			return userRepo.save(std2);
		});

		User instructor = userRepo.findByUsername("instructor").orElseGet(() -> {
			User inst = new User();
			inst.setUsername("instructor");
			inst.setEmail("instructor@gmail.com");
			inst.setPassword(passwordEncoder.encode("password"));
			inst.addRoles(instructorRole);
			return userRepo.save(inst);
		});

		User master = userRepo.findByUsername("master").orElseGet(() -> {
			User mstr = new User();
			mstr.setUsername("master");
			mstr.setEmail("master@gmail.com");
			mstr.setPassword(passwordEncoder.encode("password"));
			mstr.addRoles(admineRole);
			return userRepo.save(mstr);
		});

		insertInstructorAndCourse(instructor);
		insertStudent(student, "Student 1");
		insertStudent(student2, "Student 2");
	}

	void insertStudent(User user, String studentName) {
		Country egypt = countryRepo.findByCountryName("Egypt").orElseGet(() -> {
			Country country = new Country();
			country.setCountryName("Egypt");
			return countryRepo.save(country);
		});

		Person student = personRepo.findByName(studentName).orElseGet(() -> {
			Person person = new Person();
			person.setUser(user);
			person.setCountry(egypt);
			person.setName(studentName);
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
		if (courseService.findAll().isEmpty()) {
			Course course = new Course();
			course.setInstructor(instructor);
			course.setTitle("JAVA INTRODUCTION");
			course.setPrice(10000);
			course.setStatus(CourseStatus.PUBLIC);
			course = courseService.insert(course);
		}
	}

}
