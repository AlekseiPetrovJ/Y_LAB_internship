package ru.petrov.model;

import java.time.LocalDate;
import java.util.UUID;

public class Measurement extends AbstractEntity {
    private TypeOfValue typeOfValue;
    private LocalDate registered;
    private User user;


    public Measurement(UUID id, String name, TypeOfValue typeOfValue, User user, LocalDate registered) {
        super(id, name);
        this.typeOfValue = typeOfValue;
        this.user = user;
        this.registered = registered;
    }

    public Measurement(String name, TypeOfValue typeOfValue, LocalDate registered, User user) {
        super(UUID.randomUUID(), name);
        this.typeOfValue = typeOfValue;
        this.registered = registered;
        this.user = user;
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
}
