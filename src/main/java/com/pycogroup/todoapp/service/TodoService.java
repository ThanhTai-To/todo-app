package com.pycogroup.todoapp.service;

import com.pycogroup.todoapp.model.Task;

public interface TodoService {

    Task createTodo(String userName, Task task);

    void delete(String userName, String todoId);
}
