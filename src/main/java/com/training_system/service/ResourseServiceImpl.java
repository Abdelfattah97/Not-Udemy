package com.training_system.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.training_system.entity.dto.mapper.ResourceDto;
import com.training_system.exceptions.ResourceLoadingException;
import com.training_system.exceptions.ResourceUploadException;
import com.training_system.utils.ResourceHandler;

@Service
public class ResourseServiceImpl implements ResourceService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${lesson_resource_absolute_path}")
	private String lessonRelativePath;

	@Override
	public String upload(MultipartFile uploadedFile) {
		String lessonsPath = Paths.get(System.getProperty("user.dir"), lessonRelativePath).toString();
		return upload(uploadedFile, lessonsPath);
	}

	@Override
	public ResourceDto getLocalResource(String path) {
		ResourceHandler resourceHandler = new ResourceHandler();
		try {
			resourceHandler.loadResourceData(path);
		} catch (IOException ex) {
			throw new ResourceLoadingException(ex.getMessage());
		}
		ResourceDto dto = new ResourceDto();
		dto.setFileName(resourceHandler.getResource().getFilename());
		dto.setMediaType(MediaType.valueOf(resourceHandler.getContentType()));
		try {
			dto.setSize_MB(resourceHandler.getResource().contentLength() / (int) (1024.0 * 1024.0));
		} catch (IOException e) {
			dto.setSize_MB(null);
		}
		return dto;
	}

	@Override
	public String upload(MultipartFile uploadedFile, String path) {

		String filePath = Paths.get(path, uploadedFile.getOriginalFilename()).toString();
		logger.warn(path);
		logger.warn(filePath);
		File f = new File(filePath);
		f.getParentFile().mkdirs();

		try (FileOutputStream fos = new FileOutputStream(f)) {
			fos.write(uploadedFile.getBytes());
			fos.flush();
			return f.getAbsolutePath();
		} catch (IOException ex) {
			throw new ResourceUploadException(String.format("File upload failed due to: %s", ex.getMessage()));
		}
	}

}
