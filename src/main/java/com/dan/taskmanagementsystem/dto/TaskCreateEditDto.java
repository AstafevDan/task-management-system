package com.dan.taskmanagementsystem.dto;

import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateEditDto {

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private Long executorId;

    private Long authorId;
}
