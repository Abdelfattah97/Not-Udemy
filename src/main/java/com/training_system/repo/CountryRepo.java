package com.training_system.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Country;
import com.training_system.entity.Role;

@Repository
public interface CountryRepo extends BaseRepository<Country, Long>{

	Optional<Country> findByCountryName(String countryName);

}
