package com.training_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Disable CSRF Protection
    http.csrf(csrf -> csrf.disable());

    // Enable HTTP Basic Authentication
    http.httpBasic(Customizer.withDefaults());

    // Enable Form Authentication
    http.formLogin(form -> form.loginProcessingUrl("/authenticate").permitAll());

    // Configure Authorization
    http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    // TODO: For demo purposes only. Do not use in production.
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  UserDetailsService userDetailsService() {
    UserDetails student = User.builder()
        .username("student")
        .password("password")
        .roles("STUDENT")
        .build();

    UserDetails instructor = User.builder()
        .username("instructor")
        .password("password")
        .roles("INSTRUCTOR")
        .build();

    UserDetails master = User.builder()
        .username("master")
        .password("password")
        .roles("STUDENT", "INSTRUCTOR")
        .build();

    return new InMemoryUserDetailsManager(instructor, student, master);
  }

}