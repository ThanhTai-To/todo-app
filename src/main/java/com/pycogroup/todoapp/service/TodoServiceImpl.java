package com.pycogroup.todoapp.service;

import com.pycogroup.todoapp.exception.ResourceNotFoundException;
import com.pycogroup.todoapp.model.Task;
import com.pycogroup.todoapp.model.User;
import com.pycogroup.todoapp.repository.TaskRepository;
import com.pycogroup.todoapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTodo(String userName, Task task) {
        User currentUser = getUserByName(userName);
        if (currentUser == null ) {
            throw new ResourceNotFoundException("username-" + userName);
        }

        task.setUserId(currentUser.getUserId());
        task.setUserName(currentUser.getUserName());
        LocalDateTime createdAt = LocalDateTime.now();
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(createdAt);
        log.info("\nTodo Service: created todo");
        return taskRepository.save(task);
    }

    @Override
    public void delete(String userName, String todoId) {
        User currentUser = getUserByName(userName);
        if (currentUser == null ) {
            throw new ResourceNotFoundException("username-" + userName);
        }
        taskRepository.deleteById(todoId);
        log.info("\nDelete todo-" + todoId);
    }

    public User getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }


}
