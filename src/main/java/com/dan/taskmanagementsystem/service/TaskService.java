package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface TaskService {

    Page<TaskReadDto> findAllTasksByFilter(Long authorId, Long executorId, TaskStatus status, TaskPriority priority,
                                           Principal principal, Pageable pageable);

    TaskReadDto findTaskById(Long id, Principal principal);

    TaskReadDto createTask(TaskCreateEditDto taskCreateEditDto, Principal principal);

    TaskReadDto updateTask(Long id, TaskCreateEditDto taskCreateEditDto, Principal principal);

    boolean deleteTask(Long id, Principal principal);

    TaskReadDto updateTaskStatus(Long id, TaskStatus status, Principal principal);

    TaskReadDto updateTaskPriority(Long id, TaskPriority priority, Principal principal);

    TaskReadDto assignExecutor(Long id, Long executorId, Principal principal);
}
