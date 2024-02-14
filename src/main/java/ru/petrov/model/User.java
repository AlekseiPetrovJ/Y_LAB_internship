package ru.petrov.model;

import java.time.LocalDateTime;

public class User extends AbstractNamedEntity {
    private String password;
    private Role role;
    private LocalDateTime registered;

    public User() {
        super(null, null);
    }

    public User(Integer id, String name, String password, Role role, LocalDateTime registered) {
        super(id, name);
        this.password = password;
        this.role = role;
        this.registered = registered;
    }

    public User(Integer id, String name, String password, Role role) {
        this(id, name, password, role, LocalDateTime.now());
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
