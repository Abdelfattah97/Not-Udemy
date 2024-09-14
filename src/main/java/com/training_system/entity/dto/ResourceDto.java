package com.training_system.entity.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import lombok.Data;

@Data
public class ResourceDto {

	private Long size_MB;
	private MediaType mediaType;
	private String fileName;
	private Resource resource;
	
}
