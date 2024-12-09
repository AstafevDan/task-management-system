package com.dan.taskmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilter {

    private Long authorId;

    private Long executorId;

    private String status;

    private String priority;
}
