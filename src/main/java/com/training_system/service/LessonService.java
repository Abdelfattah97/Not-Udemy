package com.training_system.service;

import com.training_system.entity.Course;
import com.training_system.entity.dto.LessonDto;
import com.training_system.entity.enums.LessonType;
import com.training_system.exceptions.DuplicateLessonException;
import com.training_system.repo.CourseRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training_system.base.BaseServiceImpl;
import com.training_system.entity.Lesson;
import com.training_system.repo.LessonRepo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonService extends BaseServiceImpl<Lesson, Long> {

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private CourseRepo courseRepo;

    public void addLesson(LessonDto lessonDto){
        if(lessonRepo.findById(lessonDto.getId()).isPresent()){
            throw new DuplicateLessonException("This Lesson with id = " + lessonDto.getId() + " already exists");
        }
        if(lessonRepo.findByTitleAndCourse_Id(lessonDto.getTitle(), lessonDto.getCourse_id()).isPresent()){
            throw new DuplicateLessonException(String.format("This lesson with title = %s, and course_id = %d", lessonDto.getTitle(), lessonDto.getCourse_id()));
        }
        Course course = courseRepo.findById(lessonDto.getCourse_id()).orElseThrow(() -> new EntityNotFoundException("There is no course found with this id = " + lessonDto.getCourse_id()));
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setCourse(course);
        lesson.setFilePath(lessonDto.getFilePath());
        lesson.setLessonType(lessonDto.getLessonType());
        lessonRepo.save(lesson);
    }

    public LessonDto getLessonByCourseAndLessonTitle(Long course_id, String title){
        Lesson lesson = lessonRepo.findByTitleAndCourse_Id(title, course_id).orElseThrow(() -> new EntityNotFoundException("There is no lesson for this title and course id"));

        return LessonDto.fromEntityToDto(lesson);
    }

    public void updateLesson(LessonDto lessonDto){
        Lesson lesson = lessonRepo.findById(lessonDto.getId()).orElseThrow(() -> new EntityNotFoundException("There is no lesson with this id!!!"));
        Course course = courseRepo.findById(lessonDto.getCourse_id()).orElseThrow(() -> new EntityNotFoundException("There is no course with this id!!!"));

        lesson.setTitle(lessonDto.getTitle());
        lesson.setLessonType(lessonDto.getLessonType());
        lesson.setCourse(course);
        lesson.setFilePath(lessonDto.getFilePath());

        update(lesson);
    }

    public Set<LessonDto> getLessons(){
        return LessonDto.fromEntitiesToDtos(new HashSet<>(lessonRepo.findAll()));
    }

    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("There is no lesson with this id!!!"));

        lessonRepo.delete(lesson);
    }
}
