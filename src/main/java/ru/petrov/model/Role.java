package ru.petrov.model;

public enum Role {
    ROLE_ADMIN(100000),
    ROLE_USER(100001);

    private final int roleId;

    private Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
