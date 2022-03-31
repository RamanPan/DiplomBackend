package ru.ramanpan.petroprimoweb.model.enums;

public enum Permission {
    CREATE_TEST("create:test"),
    UPDATE_TEST("update:test"),
    CREATE_QUESTION("create:question"),
    UPDATE_QUESTION("update:question"),
    CREATE_ANSWER("create:answer"),
    UPDATE_ANSWER("update:answer"),
    CREATE_USER_ANSWER("create:user_answer"),
    UPDATE_USER_ANSWER("update:user_answer");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
