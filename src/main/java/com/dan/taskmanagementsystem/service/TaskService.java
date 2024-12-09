package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskReadDto> findAllTasksByFilter(Long authorId, Long executorId, String status, String priority, Pageable pageable);

    TaskReadDto findTaskById(Long id);

    TaskReadDto createTask(TaskCreateEditDto taskCreateEditDto);

    TaskReadDto updateTask(Long id, TaskCreateEditDto taskCreateEditDto);

    boolean deleteTask(Long id);

    TaskReadDto updateTaskStatus(Long id, String status);

    TaskReadDto updateTaskPriority(Long id, String priority);

    TaskReadDto assignExecutor(Long id, Long executorId);
}
