package com.dan.taskmanagementsystem.mapper;

import com.dan.taskmanagementsystem.dto.CommentReadDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.dto.UserReadDto;
import com.dan.taskmanagementsystem.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentReadMapper implements Mapper<Comment, CommentReadDto>{

    private final UserReadMapper userReadMapper;
    private final TaskReadMapper taskReadMapper;

    @Override
    public CommentReadDto map(Comment object) {

        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);

        TaskReadDto task = Optional.ofNullable(object.getTask())
                .map(taskReadMapper::map)
                .orElse(null);

        return new CommentReadDto(
                object.getId(),
                task,
                user,
                object.getContent()
        );
    }
}
