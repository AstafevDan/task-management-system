package com.dan.taskmanagementsystem.dto.filter;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentFilter {

    @NotNull(message = "Task id can not be null")
    private Long taskId;
}
