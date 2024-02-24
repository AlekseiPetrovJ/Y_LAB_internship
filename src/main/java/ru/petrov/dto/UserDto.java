package ru.petrov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserDto {
    @NotEmpty(message = "User name not might be empty")
    @Size(min = 3, max = 60, message = "User name might contain 3-60 char")
    @Schema(example = "Ivan")
    private String name;
    @Schema(example = "100003")
    private Integer id;

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
}
