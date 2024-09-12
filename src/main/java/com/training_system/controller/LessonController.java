package com.training_system.controller;

import com.training_system.entity.Course;
import com.training_system.entity.dto.LessonDto;
import com.training_system.exceptions.DuplicateLessonException;
import com.training_system.service.LessonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Lesson;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/Lesson")
public class LessonController extends BaseControllerImpl<Lesson, Long> {	
	@Autowired
    private LessonService lessonService;

    @PostMapping("/addlesson")
    public void addLesson(LessonDto lessonDto){
        lessonService.addLesson(lessonDto);
    }

    @GetMapping("/getlesson")
    public LessonDto getLessonByCourseAndLessonTitle(Long course_id, String title){
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
