package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.CommentCreateEditDto;
import com.dan.taskmanagementsystem.dto.CommentReadDto;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.mapper.CommentCreateEditMapper;
import com.dan.taskmanagementsystem.mapper.CommentReadMapper;
import com.dan.taskmanagementsystem.repository.CommentRepository;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentReadMapper commentReadMapper;
    private final CommentCreateEditMapper commentCreateEditMapper;

    @Override
    public Page<CommentReadDto> findAllCommentsByTaskId(Long taskId, Pageable pageable) {
        return commentRepository.findByTaskId(taskId, pageable)
                .map(commentReadMapper::map);
    }

    @Transactional
    @Override
    public CommentReadDto addComment(CommentCreateEditDto commentCreateEditDto) {
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
}
