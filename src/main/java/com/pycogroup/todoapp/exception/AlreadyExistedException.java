package com.pycogroup.todoapp.exception;

public class AlreadyExistedException extends RuntimeException {
    public AlreadyExistedException(String exception) {
        super(exception);
    }
}
