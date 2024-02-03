package ru.petrov.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractEntity {
    protected UUID uuid;
    protected LocalDateTime registered;


    public AbstractEntity(UUID uuid) {
        this.uuid = uuid;
    }
    public AbstractEntity(UUID uuid, LocalDateTime registered) {
        this.uuid = uuid;
        this.registered = registered;
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

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
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
