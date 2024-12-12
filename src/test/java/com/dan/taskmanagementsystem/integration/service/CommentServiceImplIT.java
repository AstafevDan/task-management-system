package com.dan.taskmanagementsystem.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import com.dan.taskmanagementsystem.dto.CommentCreateEditDto;
import com.dan.taskmanagementsystem.dto.CommentReadDto;
import com.dan.taskmanagementsystem.integration.IntegrationTestBase;
import com.dan.taskmanagementsystem.service.CommentServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WithMockUser(username = "admin@gmail.com")
public class CommentServiceImplIT extends IntegrationTestBase {

    private final CommentServiceImpl commentService;

    private static final Long TASK_ID = 1L;
    private static final Long USER_ID = 1L;

    @Test
    void testFindByFilter() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        Page<CommentReadDto> comments = commentService.findAllCommentsByTaskId(TASK_ID, principal,
         PageRequest.of(0, 1));

        assertNotNull(comments);
        assertThat(comments).hasSize(1);
    }

    @Test
    void testAddComment() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        CommentCreateEditDto dto = new CommentCreateEditDto(TASK_ID, USER_ID, "commentary");

        CommentReadDto comment = commentService.addComment(dto, principal);

        assertNotNull(comment);
        assertEquals(dto.getTaskId(), comment.getTask().getId());
        assertEquals(dto.getUserId(), comment.getUser().getId());
        assertEquals(dto.getContent(), comment.getContent());
    }
}
