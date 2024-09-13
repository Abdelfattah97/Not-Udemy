package com.training_system.entity.dto;

import com.training_system.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private Long countryId;
    private Long userId;

    public static PersonDto fromEntityToDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getCountry().getId(),
                person.getUser().getId()
        );
    }

    public static Set<PersonDto> fromEntitiesToDtos(Set<Person> persons){
        return persons.stream().map(PersonDto::fromEntityToDto).collect(Collectors.toSet());
    }
}
