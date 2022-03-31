package ru.ramanpan.petroprimoweb.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ROLE_STUDENT(Set.of(
            Permission.CREATE_USER_ANSWER,
            Permission.UPDATE_USER_ANSWER
    )),
    ROLE_PROFESSOR(Set.of(
            Permission.CREATE_TEST,
            Permission.UPDATE_TEST,
            Permission.CREATE_QUESTION,
            Permission.UPDATE_QUESTION,
            Permission.CREATE_ANSWER,
            Permission.UPDATE_ANSWER,
            Permission.CREATE_USER_ANSWER,
            Permission.UPDATE_USER_ANSWER));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).
                collect(Collectors.toSet());
    }
}
