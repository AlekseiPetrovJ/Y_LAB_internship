package ru.petrov.model;

import java.time.LocalDate;
import java.util.UUID;

public class User extends AbstractEntity {
    private String password;
    private Role role;
    private LocalDate registered;

    public User(UUID id, String name, String password, Role role, LocalDate registered) {
        super(id, name);
        this.password = password;
        this.role = role;
        this.registered = registered;
    }

    public User(UUID id, String name, String password, Role role) {
        this(id, name, password, role, LocalDate.now());
    }
    public User(String name, String password, Role role) {
        this(null, name, password, role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }
}
