package com.dan.taskmanagementsystem.unit.service;

import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.entity.enums.Role;
import com.dan.taskmanagementsystem.entity.enums.TaskPriority;
import com.dan.taskmanagementsystem.entity.enums.TaskStatus;
import com.dan.taskmanagementsystem.mapper.TaskReadMapper;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import com.dan.taskmanagementsystem.repository.UserRepository;
import com.dan.taskmanagementsystem.service.TaskServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

   @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskReadMapper taskReadMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private Principal principal;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private User currentUser;

    @Mock
    private Role role;

    @Mock
    private Task task;

    @Mock
    private TaskReadDto taskReadDto;

    @Mock
    private Pageable pageable;

    private static final Long AUTHOR_ID = 1L;
    private static final Long EXECUTOR_ID = 2L;
    private static final TaskStatus STATUS = TaskStatus.PENDING;
    private static final TaskPriority PRIORITY = TaskPriority.MEDIUM;

    @Test
    void findAllTasksByFilter() {
        when(principal.getName()).thenReturn("test_username1");
        when(userRepository.findByEmail("test_username1")).thenReturn(Optional.of(currentUser));
        when(currentUser.getRole()).thenReturn(Role.ADMIN);
        when(currentUser.getId()).thenReturn(AUTHOR_ID);
        when(taskRepository.findByFilter(eq(AUTHOR_ID), eq(EXECUTOR_ID), eq(STATUS), eq(PRIORITY), eq(pageable)))
        .thenReturn(Page.empty());

        Page<TaskReadDto> result = taskService.findAllTasksByFilter(AUTHOR_ID, EXECUTOR_ID, STATUS, PRIORITY, principal, pageable);

        assertNotNull(result);
        verify(taskRepository).findByFilter(eq(AUTHOR_ID), eq(EXECUTOR_ID), eq(STATUS), eq(PRIORITY), eq(pageable));
    }
}