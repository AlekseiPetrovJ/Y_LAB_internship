package ru.petrov.model;

import java.util.UUID;

public abstract class AbstractEntity {
    protected UUID uuid;

    public AbstractEntity(UUID uuid) {
        this.uuid = uuid;
    }

    public AbstractEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isNew() {
        return this.uuid == null;
    }

    @Override
    public String toString() {
        return String.format("%s. UUID: %s.", getClass().getSimpleName(),uuid);
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
