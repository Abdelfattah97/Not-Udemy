package com.training_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.UserType;
import com.training_system.repo.UserTypeRepo;

@Service
public class UserTypeService extends BaseServiceImpl<UserType, Long> {

	@Autowired
	UserTypeRepo userTypeRepo;

	public UserType findByTypeName(String typename) {

		return userTypeRepo.findByTypeName(typename);

	}

}
