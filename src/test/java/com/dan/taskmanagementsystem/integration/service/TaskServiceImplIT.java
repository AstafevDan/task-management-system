package com.dan.taskmanagementsystem.integration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.integration.IntegrationTestBase;
import com.dan.taskmanagementsystem.service.TaskServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WithMockUser(username = "admin@gmail.com")
public class TaskServiceImplIT extends IntegrationTestBase {

    private final TaskServiceImpl taskService;

    private static final Long TASK_ID = 1L;
    private static final Long EXECUTOR_ID = 3L;

    @Test
    void testFindByFilter() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        Page<TaskReadDto> tasks = taskService.findAllTasksByFilter(null, EXECUTOR_ID,
         null, null, principal, PageRequest.of(0, 4));

         assertNotNull(tasks);
    }

    @Test
    void testFindById() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        TaskReadDto maybeTask = taskService.findTaskById(TASK_ID, principal);

        assertNotNull(maybeTask);
        assertEquals(TASK_ID, maybeTask.getId());
    }

    @Test
    void testCreateTask() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        TaskCreateEditDto dto = new TaskCreateEditDto(
            "new task",
            "task",
            TaskStatus.PENDING,
            TaskPriority.MEDIUM,
            null,
            null
        );

        TaskReadDto actualResult = taskService.createTask(dto, principal);

        assertEquals(dto.getTitle(), actualResult.getTitle());
        assertEquals(dto.getDescription(), actualResult.getDescription());
        assertEquals(dto.getStatus(), actualResult.getStatus());
        assertEquals(dto.getPriority(), actualResult.getPriority());
    }

    @Test
    void testUpdateTask() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        TaskCreateEditDto dto = new TaskCreateEditDto(
            "updated task",
            null,
            TaskStatus.COMPLETED,
            TaskPriority.MEDIUM,
            null,
            null
        );

        TaskReadDto task = taskService.updateTask(TASK_ID, dto, principal);

        assertNotNull(task);
        assertEquals(dto.getTitle(), task.getTitle());
        assertEquals(dto.getStatus(), task.getStatus());
        assertEquals(dto.getPriority(), task.getPriority());
    }

    @Test
    void testDeleteTask() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        assertTrue(taskService.deleteTask(TASK_ID, principal));
        assertFalse(taskService.deleteTask(-100L, principal));
    }

    @Test
    void testUpdateStatus() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        TaskReadDto task = taskService.updateTaskStatus(TASK_ID, TaskStatus.COMPLETED, principal);

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void testUpdatePriority() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        TaskReadDto task = taskService.updateTaskPriority(TASK_ID, TaskPriority.HIGH, principal);

        assertEquals(TaskPriority.HIGH, task.getPriority());
    }

    @Test
    void testAssignExecutor() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        TaskReadDto task = taskService.assignExecutor(TASK_ID, EXECUTOR_ID, principal);

        assertEquals(EXECUTOR_ID, task.getExecutor().getId());
    }
}
