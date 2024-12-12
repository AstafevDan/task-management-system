package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.dto.CommentCreateEditDto;
import com.dan.taskmanagementsystem.dto.CommentReadDto;
import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentReadDto> findAllCommentsByTaskId(Long taskId, Principal principal, Pageable pageable);

    CommentReadDto addComment(CommentCreateEditDto commentCreateEditDto, Principal principal);
}
