package com.training_system.service;

import com.training_system.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Country;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryService  extends BaseServiceImpl<Country, Long>{

    @Autowired
    private CountryRepo countryRepo;

    public Set<Country> getCountries() {
        return new HashSet<>(countryRepo.findAll());
    }

    public void addCountry(Country country) {
        insert(country);
    }
}
