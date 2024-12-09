package com.dan.taskmanagementsystem.dto;

import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import lombok.Value;

@Value
public class TaskReadDto {

    Long id;

    String title;

    String description;

    TaskStatus status;

    TaskPriority priority;

    UserReadDto executor;

    UserReadDto author;
}
