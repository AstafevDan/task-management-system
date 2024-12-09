package com.dan.taskmanagementsystem.dto;

import lombok.Value;

@Value
public class CommentReadDto {

    Long id;

    TaskReadDto task;

    UserReadDto user;

    String content;
}
