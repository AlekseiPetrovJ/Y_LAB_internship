package ru.petrov.model;

import java.util.UUID;

public class Measurement extends AbstractEntity {
    private TypeOfValue typeOfValue;
    private final double value;
    private User user;
    private int year;
    private int month;


    public Measurement(UUID uuid, TypeOfValue typeOfValue, int year, int month, User user, double value) {
        super(uuid);
        this.typeOfValue = typeOfValue;
        this.user = user;
        this.year = year;
        this.month = month;
        this.value = value;
    }

    public Measurement(TypeOfValue typeOfValue, int year, int month, User user, double value) {
        this(null, typeOfValue, year, month, user, value);
    }

    public TypeOfValue getTypeOfValue() {
        return typeOfValue;
    }

    public void setTypeOfValue(TypeOfValue typeOfValue) {
        this.typeOfValue = typeOfValue;
    }

    public double getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Measurement{" + typeOfValue.getName() +
                "=" + value +
                " " + typeOfValue.getUnitOfMeasurement() +
                ", year=" + year +
                ", month=" + month +
                ", user=" + user.getName() +
                "}\n";
    }
}
