package ru.petrov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserInDto {
    @NotEmpty(message = "User name not might be empty")
    @Size(min = 3, max = 60, message = "User name might contain 3-30 char")
    private String name;
    @NotEmpty(message = "Id not might be empty")
    private Integer id;
    @NotEmpty(message = "Id not might be empty")
    private String password;

}
