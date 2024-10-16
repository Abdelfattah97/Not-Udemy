package com.training_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Disable CSRF Protection
    http.csrf(csrf -> csrf.disable());

    // Enable HTTP Basic Authentication
    http.httpBasic(Customizer.withDefaults());

    //Enable Form Authentication
    http.formLogin(form -> form
        .loginProcessingUrl("/authenticate")
        .defaultSuccessUrl("/docs")
        .failureHandler((request, response, exception) -> response.setStatus(401))
        .permitAll());

    // Configure Authorization
    http.authorizeHttpRequests(auth -> auth
    		.requestMatchers("/api/user/register").permitAll()
    		.requestMatchers("/api/course/public/search/by/*").permitAll()
    		.requestMatchers("/api/country/getcountries").permitAll()
    		.requestMatchers("/login/*").permitAll()
    		.requestMatchers("/error").permitAll()
    		.anyRequest().authenticated()
    		).anonymous(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}