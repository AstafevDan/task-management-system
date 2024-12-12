package com.dan.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignExecutorDto {

    @NotNull(message = "Executor id should be filled in")
    private Long executorId;
}
