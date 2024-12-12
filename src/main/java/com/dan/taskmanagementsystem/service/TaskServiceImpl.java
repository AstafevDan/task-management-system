package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.Permission;
import com.dan.taskmanagementsystem.entity.enums.Role;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

import static com.dan.taskmanagementsystem.entity.enums.Permission.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskCreateEditMapper taskCreateEditMapper;
    private final TaskReadMapper taskReadMapper;
    private final PermissionService permissionService;

    @Override
    public Page<TaskReadDto> findAllTasksByFilter(Long authorId, Long executorId, TaskStatus status, TaskPriority priority,
                                                  Principal principal, Pageable pageable) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();
        Long userId = user.getId();

        if (role.equals(Role.USER)) {
            executorId = userId;
        }

        return taskRepository.findByFilter(authorId, executorId, status, priority, pageable)
                .map(taskReadMapper::map);
    }

    @Override
    public TaskReadDto findTaskById(Long id, Principal principal) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();

        checkPermission(role, READ_TASK);
        return taskRepository.findById(id)
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public TaskReadDto createTask(TaskCreateEditDto taskCreateEditDto, Principal principal) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();
        Long authorId = user.getId();

        checkPermission(role, CREATE_TASK);

        taskCreateEditDto.setAuthorId(authorId);
        return Optional.of(taskCreateEditDto)
                .map(taskCreateEditMapper::map)
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow(() -> new RuntimeException("Task creation failed"));
    }

    @Transactional
    @Override
    public TaskReadDto updateTask(Long id, TaskCreateEditDto taskCreateEditDto, Principal principal) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();

        checkPermission(role, UPDATE_TASK);
        return taskRepository.findById(id)
                .map(entity -> taskCreateEditMapper.map(taskCreateEditDto, entity))
                .map(taskRepository::saveAndFlush)
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public boolean deleteTask(Long id, Principal principal) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();

        checkPermission(role, DELETE_TASK);
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
    public TaskReadDto updateTaskStatus(Long id, TaskStatus status, Principal principal) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();
        Long userId = user.getId();

        checkPermission(role, UPDATE_STATUS, id, userId);
        return taskRepository.findById(id)
                .map(entity -> {
                    entity.setStatus(status);
                    return taskRepository.saveAndFlush(entity);
                })
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public TaskReadDto updateTaskPriority(Long id, TaskPriority priority, Principal principal) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();

        checkPermission(role, UPDATE_PRIORITY);
        return taskRepository.findById(id)
                .map(entity -> {
                    entity.setPriority(priority);
                    return taskRepository.saveAndFlush(entity);
                })
                .map(taskReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Transactional
    @Override
    public TaskReadDto assignExecutor(Long id, Long executorId, Principal principal) {
        User curUser = getCurrentUser(principal);
        Role role = curUser.getRole();

        checkPermission(role, ASSIGN_EXECUTOR);
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User user = userRepository.findById(executorId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        task.setExecutor(user);
        Task savedTask = taskRepository.saveAndFlush(task);
        return taskReadMapper.map(savedTask);
    }

    private void checkPermission(Role role, Permission permission, Long taskId, Long userId) {
        if (role.equals(Role.ADMIN)) {
            if (!permissionService.hasPermission(role, permission)) {
                throw new SecurityException("You do not have permission to perform this action");
            }
        } else if (role.equals(Role.USER)) {
            if (permissionService.hasPermission(role, permission)) {
                Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
                if (!task.getExecutor().getId().equals(userId)) {
                    throw new SecurityException("You do not have permission to perform this action");
                }
            } else {
                throw new SecurityException("You do not have permission to perform this action");
            }
        } else {
            throw new SecurityException("You do not have permission to perform this action");
        }
    }

    private void checkPermission(Role role, Permission permission) {
        if (role.equals(Role.ADMIN)) {
            if (!permissionService.hasPermission(role, permission)) {
                throw new SecurityException("You do not have permission to perform this action");
            }
        }
    }

    private User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
