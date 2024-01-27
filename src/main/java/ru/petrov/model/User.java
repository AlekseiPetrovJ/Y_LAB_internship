package ru.petrov.model;

import java.util.Date;
import java.util.UUID;

public class User extends AbstractEntity {
    private String password;
    private Role role;
    private Date registered;

    public User(UUID id, String name, String password, Role role, Date registered) {
        super(id, name);
        this.password = password;
        this.role = role;
        this.registered = registered;
    }

    public User(UUID id, String name, String password, Role role) {
        this(id, name, password, role, new Date());
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

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }
}
