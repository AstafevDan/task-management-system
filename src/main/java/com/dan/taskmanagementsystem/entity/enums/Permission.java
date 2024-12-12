package com.dan.taskmanagementsystem.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    READ_TASK("admin:readTask"),
    CREATE_TASK("admin:createTask"),
    UPDATE_TASK("admin:updateTask"),
    DELETE_TASK("admin:deleteTask"),

    UPDATE_STATUS("user:updateStatus"),
    UPDATE_PRIORITY("admin:updatePriority"),
    ASSIGN_EXECUTOR("admin:assignExecutor"),
    ADD_COMMENT("user:addComment"),
    READ_COMMENTS("user:readComments");

    private final String permission;
}
