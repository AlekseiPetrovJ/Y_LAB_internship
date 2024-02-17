package ru.petrov.model;

public class TypeOfValue extends AbstractNamedEntity {
    private String unitOfMeasurement;

    public TypeOfValue() {
        super(null, null);
    }

    public TypeOfValue(Integer id, String name, String unitOfMeasurement) {
        super(id, name);
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public TypeOfValue(String name, String unitOfMeasurement) {
        super(null, name);
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
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
