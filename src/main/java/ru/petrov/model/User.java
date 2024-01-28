package ru.petrov.model;

import java.time.LocalDate;
import java.util.UUID;

public class User extends AbstractNamedEntity {
    private String password;
    private Role role;
    private LocalDate registered;

    public User(UUID uuid, String name, String password, Role role, LocalDate registered) {
        super(uuid, name);
        this.password = password;
        this.role = role;
        this.registered = registered;
    }

    public User(UUID uuid, String name, String password, Role role) {
        this(uuid, name, password, role, LocalDate.now());
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
