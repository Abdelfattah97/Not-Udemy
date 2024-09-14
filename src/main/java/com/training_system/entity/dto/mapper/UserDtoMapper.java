package com.training_system.entity.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.training_system.entity.Authority;
import com.training_system.entity.Role;
import com.training_system.entity.User;
import com.training_system.entity.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

//	@Mapping(source = "roles.authorities", target="authorities",qualifiedByName = "authoritiesNames")
	@Mapping(source = "person.name", target="name")
	@Mapping(source = "id", target="userId")
	@Mapping(source = "person.id", target="personId")
	@Mapping(source = "password", target="password",ignore=true)
	public UserDto toDto(User user);
	 
	default List<UserDto> toDto(List<User> users) {
		return users.stream().map(this::toDto).collect(Collectors.toList());
	}
	
	 @Named(value = "rolesNames")
		default List<String> rolesToNames(Set<Role> roles){
			 return roles.stream().map(r->r.getName()).collect(Collectors.toList());
		 }
	 @Named(value = "authoritiesNames")
	 default List<String> authoritiesNames(Set<Role> roles){
		   return roles.stream()
	                .flatMap(role -> role.getAuthorities().stream())
	                .map(Authority::getName)
	                .collect(Collectors.toList());
	 }

	 
	 @Mapping(source = "userId", target="id")
	 @Mapping(source = "personId", target="person.id")
	 @Mapping(source = "userId", target="person.user.id")
	 @Mapping(source = "name", target="person.name")
	public User toEntity(UserDto entity);
	
	 
}
