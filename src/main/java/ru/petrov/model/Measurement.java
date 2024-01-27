package ru.petrov.model;

import java.util.Date;
import java.util.UUID;

public class Measurement extends AbstractEntity {
    private TypeOfValue typeOfValue;
    private Date registered;
    private User user;


    public Measurement(UUID id, String name, TypeOfValue typeOfValue, User user, Date registered) {
        super(id, name);
        this.typeOfValue = typeOfValue;
        this.user = user;
        this.registered = registered;
    }

    public Measurement(UUID id, String name, TypeOfValue typeOfValue, User user) {
        this(id, name, typeOfValue, user, new Date());
    }

    public TypeOfValue getTypeOfValue() {
        return typeOfValue;
    }

    public void setTypeOfValue(TypeOfValue typeOfValue) {
        this.typeOfValue = typeOfValue;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
