package com.training_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Country;

@RestController
@RequestMapping("/api/country")
@PreAuthorize("hasRole('master')")
public class CountryController extends BaseControllerImpl<Country, Long> {

}
