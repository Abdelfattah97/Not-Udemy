package com.training_system.controller;

import com.training_system.entity.dto.PersonDto;
import com.training_system.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/getperson/{personId}")
    public PersonDto getPerson(@PathVariable(name = "personId") Long personId){
        return personService.getPersonById(personId);
    }

    @PutMapping("/updateperrson")
    public void updatePerson(@RequestBody PersonDto personDto){
        personService.updatePerson(personDto);
    }
}
