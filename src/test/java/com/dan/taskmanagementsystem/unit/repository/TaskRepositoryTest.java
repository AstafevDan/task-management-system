package com.dan.taskmanagementsystem.unit.repository;

import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryTest {

    @Mock
    private TaskRepository taskRepository;

    @Test
    void testFilter() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Task> tasks = mock(Page.class);
        when(taskRepository.findAll(Mockito.any(Pageable.class))).thenReturn(tasks);
        var response = taskRepository.findAll(pageable);
        assertThat(response).isNotNull();
    }
}
