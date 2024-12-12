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

    @Override
    public Task map(TaskCreateEditDto fromObject, Task toObject) {
        if (fromObject.getTitle() != null) {
            toObject.setTitle(fromObject.getTitle());
        }
        if (fromObject.getDescription() != null) {
            toObject.setDescription(fromObject.getDescription());
        }
        if (fromObject.getStatus() != null) {
            toObject.setStatus(fromObject.getStatus());
        }
        if (fromObject.getPriority() != null) {
            toObject.setPriority(fromObject.getPriority());
        }
        if (fromObject.getExecutorId() != null) {
            toObject.setExecutor(getUser(fromObject.getExecutorId()));
        }
        if (fromObject.getAuthorId() != null) {
            toObject.setAuthor(getUser(fromObject.getAuthorId()));
        }
        
        return toObject;
    }
}
