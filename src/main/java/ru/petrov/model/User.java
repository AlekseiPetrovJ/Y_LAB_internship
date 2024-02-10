package ru.petrov.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User extends AbstractNamedEntity {
    private String password;
    private Role role;

    private LocalDateTime registered;

    public User(UUID uuid, String name, String password, Role role, LocalDateTime registered) {
        super(uuid, name);
        this.password = password;
        this.role = role;
        this.registered = registered;
    }

    public User(UUID uuid, String name, String password, Role role) {
        this(uuid, name, password, role, LocalDateTime.now());
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

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }



}
