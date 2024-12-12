package com.dan.taskmanagementsystem.dto;

import com.dan.taskmanagementsystem.entity.enums.TaskPriority;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePriorityDto {

    @NotNull(message = "Priority can not be null (Only LOW, MEDIUM, HIGH value)")
    private TaskPriority priority;
}
