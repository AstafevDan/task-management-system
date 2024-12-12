package com.dan.taskmanagementsystem.dto;

import com.dan.taskmanagementsystem.entity.enums.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDto {

    @NotNull(message = "Status can not be null (Only PENDING, IN_PROGRESS, COMPLETED value)")
    private TaskStatus status;
}
