package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.mapper.TaskCreateEditMapper;
import com.dan.taskmanagementsystem.mapper.TaskReadMapper;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import com.dan.taskmanagementsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskCreateEditMapper taskCreateEditMapper;
    private final TaskReadMapper taskReadMapper;

    @Override
    public Page<TaskReadDto> findAllTasksByFilter(Long authorId, Long executorId, String status, String priority, Pageable pageable) {
        return taskRepository.findByFilter(authorId, executorId, status, priority, pageable)
                .map(taskReadMapper::map);
    }

    @Override
    public TaskReadDto findTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public TaskReadDto createTask(TaskCreateEditDto taskCreateEditDto) {
        return Optional.of(taskCreateEditDto)
                .map(taskCreateEditMapper::map)
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow(() -> new RuntimeException("Task creation failed"));
    }

    @Transactional
    @Override
    public TaskReadDto updateTask(Long id, TaskCreateEditDto taskCreateEditDto) {
        return taskRepository.findById(id)
                .map(entity -> taskCreateEditMapper.map(taskCreateEditDto, entity))
                .map(taskRepository::saveAndFlush)
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public boolean deleteTask(Long id) {
        return taskRepository.findById(id)
                .map(entity -> {
                    taskRepository.delete(entity);
                    taskRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public TaskReadDto updateTaskStatus(Long id, String status) {
        return taskRepository.findById(id)
                .map(entity -> {
                    entity.setStatus(TaskStatus.valueOf(status));
                    return taskRepository.saveAndFlush(entity);
                })
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public TaskReadDto updateTaskPriority(Long id, String priority) {
        return taskRepository.findById(id)
                .map(entity -> {
                    entity.setPriority(TaskPriority.valueOf(priority));
                    return taskRepository.saveAndFlush(entity);
                })
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public TaskReadDto assignExecutor(Long id, Long executorId) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User user = userRepository.findById(executorId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        task.setExecutor(user);
        Task savedTask = taskRepository.saveAndFlush(task);
        return taskReadMapper.map(savedTask);
    }
}
