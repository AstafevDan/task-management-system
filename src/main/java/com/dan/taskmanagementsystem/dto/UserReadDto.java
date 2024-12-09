package com.dan.taskmanagementsystem.dto;

import com.dan.taskmanagementsystem.entity.enums.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Long id;

    String name;

    String email;

    Role role;
}
