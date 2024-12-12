package com.dan.taskmanagementsystem.http.rest;

import com.dan.taskmanagementsystem.dto.CommentCreateEditDto;
import com.dan.taskmanagementsystem.dto.filter.CommentFilter;
import com.dan.taskmanagementsystem.dto.CommentReadDto;
import com.dan.taskmanagementsystem.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(
        summary = "Получить комментарии по задаче",
        description = "Возвращает список комментариев для указанной задачи на основе переданного фильтра",
        responses = {
            @ApiResponse(responseCode = "200", description = "Комментарии успешно найдены"),
            @ApiResponse(responseCode = "400", description = "Некорректный фильтр")
        }
    )
    @PostMapping("/filter")
    public ResponseEntity<Page<CommentReadDto>> getCommentsByTask(
            @Valid @RequestBody CommentFilter filter,
            Principal principal,
            Pageable pageable
    ) {
        Page<CommentReadDto> comments = commentService
                .findAllCommentsByTaskId(filter.getTaskId(), principal, pageable);
        return ResponseEntity.ok(comments);
    }

    @Operation(
        summary = "Создать новый комментарий",
        description = "Добавляет новый комментарий к задаче на основе переданных данных",
        responses = {
            @ApiResponse(responseCode = "201", description = "Комментарий успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания комментария")
        }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentReadDto> createComment(@Valid @RequestBody CommentCreateEditDto dto, Principal principal) {
        CommentReadDto comment = commentService.addComment(dto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
}
