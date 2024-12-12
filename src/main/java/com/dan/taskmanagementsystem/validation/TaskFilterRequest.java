package com.dan.taskmanagementsystem.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dan.taskmanagementsystem.validation.impl.TaskFilterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TaskFilterValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskFilterRequest {

    String message() default "At least one of the filter parameter should be not null";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
