package com.dan.taskmanagementsystem.validation.impl;

import org.springframework.stereotype.Component;

import com.dan.taskmanagementsystem.dto.filter.TaskFilter;
import com.dan.taskmanagementsystem.validation.TaskFilterRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class TaskFilterValidator implements ConstraintValidator<TaskFilterRequest, TaskFilter> {

    @Override
    public boolean isValid(TaskFilter value, ConstraintValidatorContext context) {
        return value.getAuthorId() != null || value.getExecutorId() != null 
        || value.getStatus() != null || value.getPriority() != null;
    }

}
