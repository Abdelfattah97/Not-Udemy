package com.training_system.service;

import com.training_system.entity.User;
import com.training_system.entity.dto.RegistrationAdminRequest;
import com.training_system.entity.dto.RegistrationRequest;
import com.training_system.entity.dto.RegistrationResponse;

public interface RegistrationService {

	
	RegistrationResponse register (RegistrationRequest registerationRequest);

	RegistrationResponse registerAdmin(RegistrationAdminRequest registrationAdminRequest);
	
}
