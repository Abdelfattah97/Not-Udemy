package com.training_system.service;

import org.springframework.web.multipart.MultipartFile;

import com.training_system.entity.dto.mapper.ResourceDto;

public interface ResourceService {

	/**
	 * handles resource upload 
	 * @param data data byte array
	 * @param path the path to where the file to be uploaded
	 * @return the file path where the file was saved
	 * @throws ResourceUploadException 
	 */
	String upload(MultipartFile uploadedFile , String path);
	
	/**
	 * handles resource upload 
	 * @param data data byte array
	 * @param path the path to where the file to be uploaded
	 * @return the file path where the file was saved
	 * @throws ResourceUploadException 
	 */
	String upload(MultipartFile uploadedFile );
	
	
	/**
     * handles local resource reading 
     * @param path the path to the resource file to be downloaded
     * @return ResourceDto containg size, mediaType ,fileName, FileInputStream
     * @throws ResourceNotFoundException if the resource file is not found at the provided path
     */
	ResourceDto getLocalResource(String path);
	
}
