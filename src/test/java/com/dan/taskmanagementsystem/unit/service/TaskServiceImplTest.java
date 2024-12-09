package com.dan.taskmanagementsystem.unit.service;

import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.Role;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import com.dan.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskService service;

    private static Task task1;
    private static Task task2;
    private static Task task3;

    private static User author;
    private static User user;

    @BeforeAll
    static void setUp() {
        author = new User();
        author.setId(1L);
        author.setName("author");
        author.setEmail("author@email.com");
        author.setPasswordHash("1234");
        author.setRole(Role.ADMIN);

        user = new User();
        user.setId(2L);
        user.setName("user");
        user.setEmail("user@email.com");
        user.setPasswordHash("1234");
        user.setRole(Role.USER);

        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Task 1 Description");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setAuthor(author);
        task1.setExecutor(user);

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Task 2 Description");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(TaskPriority.LOW);
        task2.setAuthor(author);
        task2.setExecutor(user);

        task3 = new Task();
        task3.setId(3L);
        task3.setTitle("Task 3");
        task3.setDescription("Task 3 Description");
        task3.setStatus(TaskStatus.PENDING);
        task3.setPriority(TaskPriority.MEDIUM);
        task3.setAuthor(author);
        task3.setExecutor(user);
    }

    @Test
    void findAllTasksByFilter() {
    }

    @Test
    void findTaskById() {

    }

    @Test
    void createTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void updateTaskStatus() {
    }

    @Test
    void updateTaskPriority() {
    }

    @Test
    void assignExecutor() {
    }
}