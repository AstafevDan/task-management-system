package com.dan.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateEditDto {

    @NotNull(message = "Task id can not be null")
    private Long taskId;

    private Long userId;

    @NotBlank(message = "Comment must contain content")
    private String content;
}
