package com.training_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.entity.dto.LessonDto;
import com.training_system.service.LessonService;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {	
	@Autowired
    private LessonService lessonService;

	
    @PostMapping("/addlesson")
    @PreAuthorize("hasRole('master') or(hasRole('instructor') and @lessonService.isLessonOwner(#lessonDto.id,@userService.getCurrentUser()))")
    public void addLesson(@RequestBody LessonDto lessonDto){
        lessonService.addLesson(lessonDto);
    }

    @GetMapping("/getlesson")    
    @PreAuthorize("hasRole('master') or hasRole('instructor') or hasRole('student')")
    public LessonDto getLessonByCourseAndLessonTitle(@RequestParam Long course_id, @RequestParam String title){
        return lessonService.getLessonByCourseAndLessonTitle(course_id, title);
    }

    @PutMapping("/updatelesson")
    @PreAuthorize("hasRole('master') or(hasRole('instructor') and @lessonService.isLessonOwner(#lessonDto.id,@userService.getCurrentUser()))")
    public void updateLesson(@RequestBody LessonDto lessonDto){
        lessonService.updateLesson(lessonDto);
    }

    @DeleteMapping("/deletelesson/{lesson_id}")
    @PreAuthorize("hasRole('master') or(hasRole('instructor') and @lessonService.isLessonOwner(#lessonDto.id,@userService.getCurrentUser()))")
    public void deleteLesson(@PathVariable(name = "lesson_id") Long lesson_id){
        lessonService.deleteLesson(lesson_id);
    }
}
