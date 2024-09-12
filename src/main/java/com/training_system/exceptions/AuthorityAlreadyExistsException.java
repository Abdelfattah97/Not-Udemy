package com.training_system.exceptions;

public class AuthorityAlreadyExistsException extends EntityAlreadyExistException {
	
	public AuthorityAlreadyExistsException(String message) {
		super(message);
	}

}
