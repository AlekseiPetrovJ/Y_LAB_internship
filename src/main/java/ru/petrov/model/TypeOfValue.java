package ru.petrov.model;

import java.util.UUID;

public class TypeOfValue extends AbstractNamedEntity {
    private final String unitOfMeasurement;

    public TypeOfValue(UUID uuid, String name, String unitOfMeasurement) {
        super(uuid, name);
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public TypeOfValue(String name, String unitOfMeasurement) {
        super(null, name);
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    @Override
    public String toString() {
        return "TypeOfValue{" + "name='" + name + '\'' +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                "}\n";
    }
}
