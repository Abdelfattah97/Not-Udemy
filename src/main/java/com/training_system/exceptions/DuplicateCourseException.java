package com.training_system.exceptions;

public class DuplicateCourseException extends RuntimeException{
    public DuplicateCourseException(String message){
        super(message);
    }
}
