package com.dan.taskmanagementsystem.mapper;

import com.dan.taskmanagementsystem.dto.CommentCreateEditDto;
import com.dan.taskmanagementsystem.entity.Comment;
import com.dan.taskmanagementsystem.entity.Task;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.repository.TaskRepository;
import com.dan.taskmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentCreateEditMapper implements Mapper<CommentCreateEditDto, Comment> {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    @Override
    public Comment map(CommentCreateEditDto object) {
        Comment comment = new Comment();
        copy(object, comment);
        return comment;
    }

    private void copy(CommentCreateEditDto object, Comment comment) {
        comment.setContent(object.getContent());
        comment.setTask(getTask(object.getTaskId()));
        comment.setUser(getUser(object.getUserId()));
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }

    private Task getTask(Long taskId) {
        return Optional.ofNullable(taskId)
                .flatMap(taskRepository::findById)
                .orElse(null);
    }
}
