package ru.petrov.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Measurement extends AbstractEntity {
    private TypeOfValue typeOfValue;
    private double value;
    private User user;


    public Measurement(UUID uuid, TypeOfValue typeOfValue, User user, LocalDateTime registered) {
        super(uuid, registered);
        this.typeOfValue = typeOfValue;
        this.user = user;
    }

    public Measurement(TypeOfValue typeOfValue, LocalDateTime registered, User user, double value) {
        super(null, registered);
        this.typeOfValue = typeOfValue;
        this.user = user;
        this.value = value;
    }

    public TypeOfValue getTypeOfValue() {
        return typeOfValue;
    }

    public void setTypeOfValue(TypeOfValue typeOfValue) {
        this.typeOfValue = typeOfValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Measurement{" + typeOfValue.getName() +
                "=" + value +
                " " + typeOfValue.getUnitOfMeasurement() +
                ", registered=" + registered +
                ", user=" + user.getName() +
                "}\n";
    }
}
