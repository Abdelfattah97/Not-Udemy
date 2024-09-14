package com.training_system.config;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.training_system.entity.Authority;
import com.training_system.entity.Country;
import com.training_system.entity.Course;
import com.training_system.entity.Person;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.entity.enums.CourseStatus;
import com.training_system.repo.AuthorityRepo;
import com.training_system.repo.CountryRepo;
import com.training_system.repo.PersonRepo;
import com.training_system.repo.RoleRepo;
import com.training_system.repo.UserRepo;
import com.training_system.service.CourseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

	private final UserRepo userRepo;

	private final RoleRepo roleRepo;

	private final PersonRepo personRepo;

	private final CourseService courseService;

	private final CountryRepo countryRepo;

	private final AuthorityRepo authorityRepo;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Authority enroll_course = authorityRepo.findByName("enroll_course").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("enroll_course");
			authorityRepo.save(authority);
			return authority;
		});
		Authority attend_lesson = authorityRepo.findByName("attend_lesson").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("attend_lesson");
			authorityRepo.save(authority);
			return authority;
		});
		Authority view_payment = authorityRepo.findByName("view_payment").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("view_payment");
			authorityRepo.save(authority);
			return authority;
		});
		Authority create_course = authorityRepo.findByName("create_course").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("create_course");
			authorityRepo.save(authority);
			return authority;
		});
		Authority add_lesson = authorityRepo.findByName("add_lesson").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("add_lesson");
			authorityRepo.save(authority);
			return authority;
		});
		
		Authority give_role = authorityRepo.findByName("give_role").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("give_role");
			authorityRepo.save(authority);
			return authority;
		});
		
		Authority create_role = authorityRepo.findByName("create_role").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("create_role");
			authorityRepo.save(authority);
			return authority;
		});
		
		Authority give_authority = authorityRepo.findByName("give_authority").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("give_authority");
			authorityRepo.save(authority);
			return authority;
		});
		
		
		Authority create_authority = authorityRepo.findByName("create_authority").orElseGet(() -> {
			Authority authority = new Authority();
			authority.setName("create_authority");
			authorityRepo.save(authority);
			return authority;
		});
		
		
		

		Role instructorRole = roleRepo.findByName("instructor").orElseGet(() -> {
			Role role = new Role();
			role.setName("instructor");
			Set<Authority> auths = new HashSet<Authority>();
			auths.add(create_course);
			auths.add(add_lesson);
			role.addAuthority(auths);
			return roleRepo.save(role);
		});

		Role studentRole = roleRepo.findByName("student").orElseGet(() -> {
			Role role = new Role();
			role.setName("student");
			Set<Authority> auths = new HashSet<Authority>();
			auths.add(enroll_course);
			auths.add(attend_lesson);
			auths.add(view_payment);
			role.setAuthorities(auths);
			return roleRepo.save(role);
		});

		Role admineRole = roleRepo.findByName("master").orElseGet(() -> {
			Role role = new Role();
			role.setName("master");
			role.addAuthority(authorityRepo.findAll().stream().collect(Collectors.toSet()));
			return roleRepo.save(role);
		});
		
		Role privilege_manager = roleRepo.findByName("privilege_manager").orElseGet(() -> {
			Role role = new Role();
			role.setName("privilege_manager");
			role.addAuthority(create_authority,create_role,give_authority,give_role);
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
			mstr.addRoles(admineRole,privilege_manager);
			return userRepo.save(mstr);
		});
		

		insertInstructorAndCourse(instructor);
		insertPerson(student, "Student 1");
		insertPerson(student2, "Student 2");
		insertPerson(master, "master");
	}

	private Person insertPerson(User user, String name) {
		Country egypt = countryRepo.findByCountryName("Egypt").orElseGet(() -> {
			Country country = new Country();
			country.setCountryName("Egypt");
			return countryRepo.save(country);
		});

		return personRepo.findByName(name).orElseGet(() -> {
			Person person = new Person();
			person.setUser(user);
			person.setCountry(egypt);
			person.setName(name);
			return personRepo.save(person);
		});

	}

	private void insertInstructorAndCourse(User user) {
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

	private void insertCourse(Person instructor) {
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
