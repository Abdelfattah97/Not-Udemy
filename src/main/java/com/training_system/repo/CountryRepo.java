package com.training_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training_system.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Long>{

}
