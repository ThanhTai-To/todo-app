package com.pycogroup.todoapp.controller;

import com.pycogroup.todoapp.api.ApiApi;
import com.pycogroup.todoapp.api.model.CreateTodoRequest;
import com.pycogroup.todoapp.api.model.ObjectCreationSuccessResponse;
import com.pycogroup.todoapp.api.model.TodoListResponse;
import com.pycogroup.todoapp.api.model.UpdateTodoRequest;
import com.pycogroup.todoapp.model.Task;
import com.pycogroup.todoapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class TodoController implements ApiApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TodoService todoService;

    @Override
    public ResponseEntity<TodoListResponse> getTodoList() {
        return null;
    }

    @Override
    public ResponseEntity<ObjectCreationSuccessResponse> createTodo(@Valid CreateTodoRequest createTodoRequest) {
        String currentUserName = getCurrentUserName();

        Task task = modelMapper.map(createTodoRequest, Task.class);
        log.info("\nTask " + task.toString());

        Task persistTask = todoService.createTodo(currentUserName, task);
        log.info("\n Created task: " + persistTask.toString());

        ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
        result.setId(persistTask.getTaskId());
        result.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ObjectCreationSuccessResponse> updateTodo(String todoId, UpdateTodoRequest updateTodoRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ObjectCreationSuccessResponse> deleteTodo(String todoId) {
        String currentUserName = getCurrentUserName();
        log.info("\nTodoController: Start delete todo");
        todoService.delete(currentUserName, todoId);
        ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
        result.setId(todoId);
        result.setResponseCode(HttpStatus.OK.value());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("\nAuthentication " + authentication.toString());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return  userDetails.getUsername();
    }
}
