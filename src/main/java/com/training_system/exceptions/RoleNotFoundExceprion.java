package com.training_system.exceptions;

public class RoleNotFoundExceprion extends RuntimeException {

	public RoleNotFoundExceprion(String message) {
		super(message);
	}
	public RoleNotFoundExceprion(String message,String...notFoundedRoles) {
		super(message);
	}

}
