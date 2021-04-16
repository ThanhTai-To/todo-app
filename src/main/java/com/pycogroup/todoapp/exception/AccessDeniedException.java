package com.pycogroup.todoapp.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String exception) {
        super(exception);
    }
}
