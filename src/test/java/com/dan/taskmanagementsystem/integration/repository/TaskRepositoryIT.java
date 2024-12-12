package com.dan.taskmanagementsystem.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.Role;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.integration.IntegrationTestBase;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import com.dan.taskmanagementsystem.repository.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskRepositoryIT extends IntegrationTestBase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    private static final Long AUTHOR_ID = 1L;
    private static final Long EXECUTOR_ID = 2L;
    private static final Long TASK_ID = 3L;
    private static final Long TASK_ID_SAVE = 6L;

    @Test
    void testFindByFilter() {
        Page<Task> tasks = taskRepository.findByFilter(AUTHOR_ID, EXECUTOR_ID,
         null, null, PageRequest.of(0, 2));
        
         assertNotNull(tasks);
         assertThat(tasks).hasSize(2);
    }

    @Test
    void testFindById() {
        Optional<Task> maybeTask =  taskRepository.findById(TASK_ID);

        assertTrue(maybeTask.isPresent());
        assertEquals(TASK_ID, maybeTask.get().getId());
    }

    @Test
    void testSave() {
        User author = User.builder()
        .id(4L)
        .email("mail@gmail.com")
        .passwordHash("1234")
        .name("name1")
        .role(Role.ADMIN)
        .build();

        User executor = User.builder()
        .id(5L)
        .email("mail_user@gmail.com")
        .passwordHash("1234")
        .name("name2")
        .role(Role.USER)
        .build();

        Task task = new Task(
            TASK_ID_SAVE,
            "Test_task",
            "desc1",
            TaskStatus.PENDING,
            TaskPriority.LOW,
            author, 
            executor
        );

        userRepository.saveAndFlush(author);
        userRepository.saveAndFlush(executor);
        Task createdTask = taskRepository.saveAndFlush(task);

        assertNotNull(createdTask);
        assertEquals(TASK_ID_SAVE, createdTask.getId());
    }

    @Test
    void testUpdate() {
        Optional<Task> task = taskRepository.findById(TASK_ID);
        assertEquals(TaskStatus.IN_PROGRESS, task.get().getStatus());
        
        task.ifPresent(task1 -> {
            task1.setStatus(TaskStatus.PENDING);
            taskRepository.saveAndFlush(task1);
        });

        Optional<Task> updatedTask = taskRepository.findById(TASK_ID);

        updatedTask.ifPresent(task1 -> assertEquals(TaskStatus.PENDING, task1.getStatus()));
    }

    @Test
    void testDelete() {
        var maybeTask = taskRepository.findById(TASK_ID);
        assertTrue(maybeTask.isPresent());
        maybeTask.ifPresent(taskRepository::delete);
        entityManager.flush();
        assertTrue(taskRepository.findById(TASK_ID).isEmpty());
    }
}
