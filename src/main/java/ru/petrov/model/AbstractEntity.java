package ru.petrov.model;

import java.util.UUID;

public abstract class AbstractEntity {
    protected UUID uuid;
    protected String name;

    public AbstractEntity(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public AbstractEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNew() {
        return this.uuid == null;
    }

    @Override
    public String toString() {
        return String.format("%s. UUID: %s. Name: %s.", getClass().getSimpleName(),uuid,name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass())
            return false;
        AbstractEntity that = (AbstractEntity) o;
        return uuid != null && uuid.equals(that.uuid);
    }

}
