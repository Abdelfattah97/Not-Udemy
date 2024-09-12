package com.training_system.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Role;
import com.training_system.service.RoleService;


@RestController
@RequestMapping("/api/role")
@PreAuthorize("hasRole('privilege_manager')")
public class RoleController extends BaseControllerImpl<Role, Long> {
	
	@Autowired
	RoleService roleService;
	
	@GetMapping
	public List<Role> findAll() {
		return roleService.findAll();
	}

	
	@GetMapping("/{id}")
	public Role findById(@PathVariable Long id) {
		return roleService.findById(id);
	}

	
	@PostMapping
	public Role Insert(@RequestBody Role entity) {
		return roleService.insert(entity);
	}

	
	@PutMapping
	public Role update(@RequestBody Role entity) {
		return roleService.update(entity);
	}
	
	@PutMapping("/{role_id}/add-authority")
	public Role addAuthority(@PathVariable Long role_id,@RequestBody Set<String> authorities) {
		return roleService.addAuthority(role_id,authorities);
	}
	
}
