package com.training_system.exceptions;

public class RoleAlreadyExistsException extends EntityAlreadyExistException {
	
	public RoleAlreadyExistsException(String message) {
		super(message);
	}

}
