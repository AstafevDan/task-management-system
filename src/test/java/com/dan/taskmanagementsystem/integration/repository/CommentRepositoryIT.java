package com.dan.taskmanagementsystem.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.dan.taskmanagementsystem.entity.Comment;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.Role;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.integration.IntegrationTestBase;
import com.dan.taskmanagementsystem.repository.CommentRepository;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import com.dan.taskmanagementsystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryIT extends IntegrationTestBase {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository; 

    private static final Long COMMENT_ID = 1L;
    private static final Long COMMENT_ID_SAVE = 4L;

    @Test
    void testFindByFilter() {
        Page<Comment> comments = commentRepository.findByTaskId(COMMENT_ID, PageRequest.of(0, 1));

        assertThat(comments).hasSize(1);
    }

    @Test
    void testSave() {
        User executor = User.builder()
        .id(4L)
        .email("mail_user@gmail.com")
        .passwordHash("1234")
        .name("name2")
        .role(Role.ADMIN)
        .build();

        Task task = new Task(
            6L,
            "Test_task",
            "desc1",
            TaskStatus.PENDING,
            TaskPriority.LOW,
            executor,
            null
        );

        Comment comment = new Comment(COMMENT_ID_SAVE, task, executor, "Hello World!");

        userRepository.saveAndFlush(executor);
        taskRepository.saveAndFlush(task);
        Comment createdComment = commentRepository.saveAndFlush(comment);

        assertNotNull(createdComment);
        assertEquals(COMMENT_ID_SAVE, createdComment.getId());
    }
}
