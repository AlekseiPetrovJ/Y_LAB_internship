package ru.petrov.model;

public enum Role {
    ADMIN(1000),
    USER(1001);

    private final int roleId;

    private Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
