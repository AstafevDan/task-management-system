package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.CommentCreateEditDto;
import com.dan.taskmanagementsystem.dto.CommentReadDto;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.Permission;
import com.dan.taskmanagementsystem.entity.enums.Role;
import com.dan.taskmanagementsystem.mapper.CommentCreateEditMapper;
import com.dan.taskmanagementsystem.mapper.CommentReadMapper;
import com.dan.taskmanagementsystem.repository.CommentRepository;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentReadMapper commentReadMapper;
    private final CommentCreateEditMapper commentCreateEditMapper;
    private final PermissionService permissionService;

    @Override
    public Page<CommentReadDto> findAllCommentsByTaskId(Long taskId, Principal principal, Pageable pageable) {
        User user = getCurrentUser(principal);
        Role role = user.getRole();
        Long userId = user.getId();

        checkPermission(role, READ_COMMENTS, taskId, userId);
        return commentRepository.findByTaskId(taskId, pageable)
                .map(commentReadMapper::map);
    }

    @Transactional
    @Override
    public CommentReadDto addComment(CommentCreateEditDto commentCreateEditDto, Principal principal) {
        User curUser = getCurrentUser(principal);
        Role role = curUser.getRole();
        Long userId = curUser.getId();

        checkPermission(role, ADD_COMMENT, commentCreateEditDto.getTaskId(), userId);

        commentCreateEditDto.setUserId(userId);

        Task task = taskRepository.findById(commentCreateEditDto.getTaskId()).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User user = userRepository.findById(commentCreateEditDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return Optional.of(commentCreateEditDto)
                .map(commentCreateEditMapper::map)
                .map(entity -> {
                    entity.setTask(task);
                    entity.setUser(user);
                    return commentRepository.save(entity);
                })
                .map(commentReadMapper::map)
                .orElseThrow(() -> new RuntimeException("Comment creation failed"));
    }

    private void checkPermission(Role role, Permission permission, Long taskId, Long userId) {
        if (role.equals(Role.ADMIN)) {
            if (!permissionService.hasPermission(role, permission)) {
                throw new SecurityException("You do not have permission to access this resource");
            }
        } else if (role.equals(Role.USER)) {
            Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
            if (!task.getExecutor().getId().equals(userId)) {
                throw new SecurityException("You do not have permission to access this resource");
            }
        } else {
            throw new SecurityException("You do not have permission to access this resource");
        }
    }

    private User getCurrentUser(Principal principal) {
        String email = principal.getName();

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
