package com.training_system.entity.dto.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.training_system.entity.Person;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.entity.dto.RegistrationRequest;
import com.training_system.entity.dto.RegistrationResponse;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

	
	 User toUserEntity(RegistrationRequest registerationRequest);
	
	 @Mapping(source = "countryId" , target="country.id")
	 Person toPersonEntity(RegistrationRequest registerationRequest);
	 
	 @Mapping(source="user.roles" , target="roles",qualifiedByName = "rolesNames")
	 @Mapping(source = "user.id" , target="userId")
	 @Mapping(source = "person.id" , target="personId")
	 RegistrationResponse toResponseDto(Person person , User user);
	 
	 @Named(value = "rolesNames")
	default Set<String> rolesToNames(Set<Role> roles){
		 return roles.stream().map(r->r.getName()).collect(Collectors.toSet());
	 }
	 
//	 @Named(value = "namesToRoles")
//	 default Set<Role> namesToRoles(Set<String> roles){
//		 return roles.stream().map(str->{
//			 Role role = new Role(); 
//			 role.setName(str); 
//			 return role; }).collect(Collectors.toSet());
//	 }
	 
}
