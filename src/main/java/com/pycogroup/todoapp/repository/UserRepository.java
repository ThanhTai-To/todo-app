package com.pycogroup.todoapp.repository;

import com.pycogroup.todoapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends MongoRepository<User, String>,
                            QuerydslPredicateExecutor<User> {
    User findByUserName(String username);
}
