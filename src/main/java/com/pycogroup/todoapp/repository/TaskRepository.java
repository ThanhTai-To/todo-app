package com.pycogroup.todoapp.repository;

import com.pycogroup.todoapp.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TaskRepository extends MongoRepository<Task, String>,
        QuerydslPredicateExecutor<Task> {
}
