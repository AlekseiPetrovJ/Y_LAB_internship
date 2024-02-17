package ru.petrov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserInDto {
    @NotEmpty(message = "User name not might be empty")
    @Size(min = 3, max = 60, message = "User name might contain 3-30 char")
    private String name;
    @NotEmpty(message = "Id not might be empty")
    private Integer id;
    @NotEmpty(message = "Password not might be empty")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        String sb = "UserInDto{" + "name='" + name + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                '}';
        return sb;
    }
}
