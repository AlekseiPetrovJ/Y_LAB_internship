package ru.petrov.model;

import java.util.UUID;

public abstract class AbstractNamedEntity extends AbstractEntity {
    protected String name;

    public AbstractNamedEntity(UUID uuid, String name) {
        super(uuid);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s. UUID: %s. Name: %s.", getClass().getSimpleName(),uuid,name);
    }
}
