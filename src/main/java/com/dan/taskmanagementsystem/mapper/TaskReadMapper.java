package com.dan.taskmanagementsystem.mapper;

import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.dto.UserReadDto;
import com.dan.taskmanagementsystem.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskReadMapper implements Mapper<Task, TaskReadDto> {

    private final UserReadMapper mapper;

    @Override
    public TaskReadDto map(Task object) {

        UserReadDto executor = Optional.ofNullable(object.getExecutor())
                .map(mapper::map)
                .orElse(null);

        UserReadDto author = Optional.ofNullable(object.getAuthor())
                .map(mapper::map)
                .orElse(null);

        return new TaskReadDto(
                object.getId(),
                object.getTitle(),
                object.getDescription(),
                object.getStatus(),
                object.getPriority(),
                executor,
                author
        );
    }
}
