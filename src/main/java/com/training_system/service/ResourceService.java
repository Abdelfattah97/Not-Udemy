package com.training_system.service;

import org.springframework.web.multipart.MultipartFile;

import com.training_system.entity.dto.ResourceDto;
import com.training_system.exceptions.ResourceLoadingException;

public interface ResourceService {

	/**
	 * handles resource upload 
	 * @param uploadedFile Multipart Object of the data to be saved
	 * @param path the path to where the file to be uploaded
	 * @return the file path where the file was saved
	 * @throws ResourceUploadException 
	 */
	String upload(MultipartFile uploadedFile , String path);
	
	/**
	 * handles resource upload , saved by default in ../root/media/lesson or as configured in properties
	 * @param data data byte array
	 * @return the file path where the file was saved
	 * @throws ResourceUploadException 
	 */
	String upload(MultipartFile uploadedFile);
	
	
	/**
     * handles local resource reading 
     * @param path the path to the resource file to be downloaded
     * @return ResourceDto containing size, mediaType ,fileName, resource
     * @throws ResourceLoadingException if the resource file is not found at the provided path
     */
	ResourceDto getLocalResource(String path);
	
}
