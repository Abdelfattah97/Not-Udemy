package com.training_system.exceptions;

public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException(String message){
        super(message);
    }
}
