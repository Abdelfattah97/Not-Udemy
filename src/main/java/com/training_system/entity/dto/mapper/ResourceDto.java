package com.training_system.entity.dto.mapper;

import java.io.FileInputStream;

import org.springframework.http.MediaType;

import lombok.Data;

@Data
public class ResourceDto {

	private Long size_MB;
	private MediaType mediaType;
	private String fileName;
	private FileInputStream dataInputStream;
	
}
