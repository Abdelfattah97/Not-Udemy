package com.training_system.service;

import com.training_system.entity.Country;
import com.training_system.entity.dto.PersonDto;
import com.training_system.repo.CountryRepo;
import com.training_system.repo.PersonRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Person;


@Service
public class PersonService  extends BaseServiceImpl<Person, Long>{
	@Autowired
    private PersonRepo personRepo;

    @Autowired
    private CountryRepo countryRepo;

    public PersonDto getPersonById(Long personId){
        Person person = personRepo.findById(personId).orElseThrow(() -> new EntityNotFoundException("There is no person with this id!!!"));
        return PersonDto.fromEntityToDto(person);
    }

    public void updatePerson(PersonDto personDto){
        Person person = personRepo.findById(personDto.getId()).orElseThrow(() -> new EntityNotFoundException("There is no person with this id!!!"));
        Country country = countryRepo.findById(personDto.getCountryId()).orElseThrow(() -> new EntityNotFoundException("There is no country with this id"));
        person.setCountry(country);
        person.setName(personDto.getName());
        update(person);
    }
}
