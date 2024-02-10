package ru.petrov.model;

public enum Role {
    ADMIN(100000),
    USER(100001);

    private final int roleId;

    private Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
