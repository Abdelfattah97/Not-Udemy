package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Lesson;
import com.training_system.entity.dto.LessonDto;
import com.training_system.service.LessonService;
import com.training_system.service.ResourceService;

@RestController
@RequestMapping("/api/lesson")

public class LessonController extends BaseControllerImpl<Lesson, Long> {	
	@Autowired
    private LessonService lessonService;
	
	@Autowired 
	private ResourceService resourceService;

	
	
	@PostMapping("/upload")
	public String uploadLessonResource(@RequestParam("file") MultipartFile uploadedfile){
		return resourceService.upload(uploadedfile);
	}
	
    @PostMapping("/addlesson")
    public void addLesson(@RequestPart("lesson") LessonDto lessonDto, @RequestPart("file") MultipartFile uploadedFile){
        if(uploadedFile != null){
            String filePath = resourceService.upload(uploadedFile);
            lessonDto.setFilePath(filePath);
        }
        lessonService.addLesson(lessonDto);
    }

    @GetMapping("/getlesson")
    public LessonDto getLessonByCourseAndLessonTitle(@RequestParam Long course_id, @RequestParam String title){
        return lessonService.getLessonByCourseAndLessonTitle(course_id, title);
    }

    @PutMapping("/updatelesson")
    public void updateLesson(@RequestBody LessonDto lessonDto){
        lessonService.updateLesson(lessonDto);
    }

    @DeleteMapping("/deletelesson/{lesson_id}")
    public void deleteLesson(@PathVariable(name = "lesson_id") Long lesson_id){
        lessonService.deleteLesson(lesson_id);
    }
}
