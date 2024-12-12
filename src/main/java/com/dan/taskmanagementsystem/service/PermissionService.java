package com.dan.taskmanagementsystem.service;

import com.dan.taskmanagementsystem.entity.enums.Permission;
import com.dan.taskmanagementsystem.entity.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    public boolean hasPermission(Role role, Permission permission) {
        if (role.equals(Role.ADMIN)) {
            return Role.ADMIN.getPermissions().contains(permission);
        } else if (role.equals(Role.USER)) {
            return Role.USER.getPermissions().contains(permission);
        }
        return false;
    }
}
