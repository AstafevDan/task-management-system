package com.dan.taskmanagementsystem.dto.filter;

import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.validation.TaskFilterRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TaskFilterRequest
public class TaskFilter {

    private Long authorId;

    private Long executorId;

    private TaskStatus status;

    private TaskPriority priority;
}
