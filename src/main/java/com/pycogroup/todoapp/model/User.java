package com.pycogroup.todoapp.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Getter
    private String userId;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private LocalDateTime createdAt;
}
