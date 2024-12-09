package com.dan.taskmanagementsystem.mapper;

import com.dan.taskmanagementsystem.dto.UserReadDto;
import com.dan.taskmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto>{
    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getName(),
                object.getEmail(),
                object.getRole()
        );
    }
}
