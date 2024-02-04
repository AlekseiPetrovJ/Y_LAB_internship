package ru.petrov.model;

import java.util.UUID;

public class Measurement extends AbstractEntity {
    private TypeOfValue typeOfValue;
    private double value;
    private User user;
    private int year;
    private int month;


    public Measurement(UUID uuid, TypeOfValue typeOfValue, User user, int year, int month) {
        super(uuid);
        this.typeOfValue = typeOfValue;
        this.user = user;
        this.year = year;
        this.month = month;
    }

    public Measurement(TypeOfValue typeOfValue, int year, int month, User user, double value) {
        super(null);
        this.typeOfValue = typeOfValue;
        this.year = year;
        this.month = month;
        this.user = user;
        this.value = value;
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
