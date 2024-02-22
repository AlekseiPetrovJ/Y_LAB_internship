package ru.petrov.dto;

import jakarta.validation.constraints.NotEmpty;

public class TypeOfValueDto {
    private Integer id;
    @NotEmpty(message = "Name not might be empty")
    private String name;
    @NotEmpty(message = "Unit of measurement name not might be empty")
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
