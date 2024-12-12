package com.dan.taskmanagementsystem.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dan.taskmanagementsystem.validation.impl.TaskInfoValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TaskInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskInfo {

    String message() default "Title should be filled in";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
