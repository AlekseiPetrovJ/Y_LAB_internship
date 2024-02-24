package ru.petrov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class TypeOfValueDto {
    @Schema(example = "100000")
    private Integer id;
    @NotEmpty(message = "Name not might be empty")
    @Schema(example = "hot water")
    private String name;
    @NotEmpty(message = "Unit of measurement name not might be empty")
    @Schema(example = "m3")
    private String unitOfMeasurement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
