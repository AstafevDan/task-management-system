package com.dan.taskmanagementsystem.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dan.taskmanagementsystem.entity.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(
            READ_TASK,
            CREATE_TASK,
            UPDATE_TASK,
            DELETE_TASK,
            UPDATE_STATUS,
            UPDATE_PRIORITY,
            ASSIGN_EXECUTOR,
            ADD_COMMENT,
            READ_COMMENTS
    )),

    USER(Set.of(
                UPDATE_STATUS,
                ADD_COMMENT,
                READ_COMMENTS
            ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
