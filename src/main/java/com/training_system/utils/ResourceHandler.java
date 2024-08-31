package com.training_system.utils;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceHandler {
	private Resource resource;
	private String contentType;
	
	final public void loadResourceData(String filePath) throws IOException {
		if(filePath.contains("http")) { // This is URL
			loadResourceFromUrl(filePath);
		}
		else if(filePath.contains(Character.toString(':'))) { // This is file system path 
			loadResourceFromFileSystem(filePath);
		}
		else { // This is ClassPath
			loadResourceFromClassPath(filePath);
		}
	}
	
	final private void loadResourceFromClassPath(String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        String contentType = Files.probeContentType(resource.getFile().toPath());
        this.resource = resource;
        this.contentType = contentType;
	}
	
	final private void loadResourceFromFileSystem(String filePath) throws IOException {
		Resource resource = new FileSystemResource(filePath);
        String contentType = Files.probeContentType(resource.getFile().toPath());
        this.resource = resource;
        this.contentType = contentType;
	}

	final private void loadResourceFromUrl(String url) throws IOException {
        Resource resource = new UrlResource(url);
        URLConnection connection = resource.getURL().openConnection();
        String contentType = connection.getContentType();
        this.resource = resource;
        this.contentType = contentType;
	}
}
