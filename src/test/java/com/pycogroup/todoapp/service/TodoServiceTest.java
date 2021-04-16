package com.pycogroup.todoapp.service;

import com.pycogroup.todoapp.model.Task;
import com.pycogroup.todoapp.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoServiceTest {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private TodoServiceImpl todoServiceImpl;

    private User initUser() {
        mongoOperations.remove(User.class);
        return mongoOperations.save(User.builder()
                .userName(RandomStringUtils.random(20))
                .password("root")
                .build());
    }

    private Task initTask(User user) {
        mongoOperations.remove(Task.class);
        return mongoOperations.save(Task.builder()
                .task(RandomStringUtils.random(30))
                .build());
    }

    @Test
    public void testCreateTodo() {
        User user = initUser();
        Task task = initTask(user);
        Task createdTask = todoServiceImpl.createTodo(user.getUserName(), task);
        Assert.assertNotNull(createdTask);
        Assert.assertEquals(user.getUserId(), task.getUserId());
        Assert.assertEquals(user.getUserName(), task.getUserName());
        Assert.assertEquals(task.getTask(), createdTask.getTask());

        LocalDateTime now = LocalDateTime.now();
        task.setUpdatedAt(now);
        Assert.assertEquals(now, task.getUpdatedAt());
    }

    @Test
    public void testGetUserByName() {
        User expected = initUser();
        User actual = todoServiceImpl.getUserByName(expected.getUserName());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getUserName(), actual.getUserName());
        Assert.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    public void testDelete() {
        User user = initUser();
        Task task = initTask(user);
        todoServiceImpl.delete(user.getUserName(), task.getTaskId());
        Assert.assertNull(mongoOperations.findById(task.getTaskId(), Task.class));
    }


}
