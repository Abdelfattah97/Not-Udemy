package com.training_system.entity;

import java.util.HashSet;
import java.util.Set;
import com.training_system.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="usr")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity<Long>  {
	@Column(nullable = false,unique = true)
	private String username;
//	@JsonIgnore
	@Column(nullable = false)
	private String password;
	@Column(nullable = false,unique = true)
	private String email;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Person person;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();

	public void addRoles(Role... roles) {
		this.roles.addAll(Set.of(roles));
	}
	
}
