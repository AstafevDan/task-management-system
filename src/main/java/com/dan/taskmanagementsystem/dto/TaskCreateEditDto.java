package com.dan.taskmanagementsystem.dto;

import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.validation.TaskInfo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TaskInfo
public class TaskCreateEditDto {

    @Size(min = 1, max = 64)
    private String title;

    private String description;

    @NotNull(message = "Status can not be null (Only PENDING, IN_PROGRESS, COMPLETED value)")
    private TaskStatus status;

    @NotNull(message = "Priority can not be null (Only LOW, MEDIUM, HIGH value)")
    private TaskPriority priority;

    private Long executorId;

    private Long authorId;
}
