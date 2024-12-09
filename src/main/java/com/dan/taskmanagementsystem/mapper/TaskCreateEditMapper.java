package com.dan.taskmanagementsystem.mapper;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskCreateEditMapper implements Mapper<TaskCreateEditDto, Task>{

    private final UserRepository userRepository;

    @Override
    public Task map(TaskCreateEditDto object) {
        Task task = new Task();
        copy(object, task);
        return task;
    }

    private void copy(TaskCreateEditDto object, Task task) {
        task.setTitle(object.getTitle());
        task.setDescription(object.getDescription());
        task.setStatus(object.getStatus());
        task.setPriority(object.getPriority());
        task.setExecutor(getUser(object.getExecutorId()));
        task.setAuthor(getUser(object.getAuthorId()));
    }

    private User getUser(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
