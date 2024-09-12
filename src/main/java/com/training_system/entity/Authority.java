package com.training_system.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training_system.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Authority extends BaseEntity<Long> implements GrantedAuthority{

	private static final long serialVersionUID = 3167450423840556878L;

	private String name;
	
	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.name;
	}
	

}
