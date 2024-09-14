package com.training_system.controller;



import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.Country;
import com.training_system.service.CountryService;

@RestController
@RequestMapping("/api/country")
@PreAuthorize("hasRole('master')")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/getcountries")
    public Set<Country> getCountries(){
        return countryService.getCountries();
    }

    @PostMapping("/addcountry")
    public void addCountry(@RequestBody Country country){
        countryService.addCountry(country);
    }

}
