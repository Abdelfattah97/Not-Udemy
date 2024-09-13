package com.training_system.controller;

import com.training_system.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Country;

import java.util.Set;

@RestController
@RequestMapping("/api/country")
public class CountryController extends BaseControllerImpl<Country, Long> {
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
