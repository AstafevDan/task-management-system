package com.dan.taskmanagementsystem.validation.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.validation.TaskInfo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class TaskInfoValidator implements ConstraintValidator<TaskInfo, TaskCreateEditDto> {
    
    @Override
    public boolean isValid(TaskCreateEditDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getTitle());        
    }
}
