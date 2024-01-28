package ru.petrov.model;

import java.time.LocalDate;
import java.util.UUID;

public class Measurement extends AbstractEntity {
    private TypeOfValue typeOfValue;
    private double value;
    private LocalDate registered;
    private User user;


    public Measurement(UUID id, TypeOfValue typeOfValue, User user, LocalDate registered) {
        super(id);
        this.typeOfValue = typeOfValue;
        this.user = user;
        this.registered = registered;
    }

    public Measurement(TypeOfValue typeOfValue, LocalDate registered, User user, double value) {
        super(null);
        this.typeOfValue = typeOfValue;
        this.registered = registered;
        this.user = user;
        this.value = value;
    }

    public TypeOfValue getTypeOfValue() {
        return typeOfValue;
    }

    public void setTypeOfValue(TypeOfValue typeOfValue) {
        this.typeOfValue = typeOfValue;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Measurement{typeOfValue=" + typeOfValue +
                ", value=" + value +
                ", registered=" + registered +
                ", user=" + user.getName() +
                "}\n";
    }
}
