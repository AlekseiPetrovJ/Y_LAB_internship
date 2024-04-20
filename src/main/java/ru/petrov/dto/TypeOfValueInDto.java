package ru.petrov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class TypeOfValueInDto {
    @NotEmpty(message = "Name not might be empty")
    @Schema(example = "hot water")
    private String name;
    @NotEmpty(message = "Unit of measurement name not might be empty")
    @Schema(example = "m3")
    private String unitOfMeasurement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }
}