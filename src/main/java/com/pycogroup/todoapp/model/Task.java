package com.pycogroup.todoapp.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tasks")
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Getter
    private String taskId;

    @Getter
    @Setter
    private String task;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    @Builder.Default
    private Boolean completed = false;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime updatedAt;
}
