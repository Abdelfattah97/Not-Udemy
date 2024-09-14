package com.training_system.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training_system.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Role extends BaseEntity<Long> implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_authorities" 
	,joinColumns = @JoinColumn(name = "role_id",nullable = false)
	,inverseJoinColumns = @JoinColumn(name = "authority_id",nullable = false)
	,uniqueConstraints = @UniqueConstraint(columnNames = {"role_id","authority_id"}))
	private Set<Authority> authorities = new HashSet<>();

	public void addAuthority(Authority...authority) {
		this.authorities.addAll(Set.of(authority));
	}
	public void addAuthority(Set<Authority> authorities) {
		this.authorities.addAll(authorities);
	}
	@Override
	@JsonIgnore
	public String getAuthority() {
		return String.format("ROLE_%s", getName());
	}

	
}
