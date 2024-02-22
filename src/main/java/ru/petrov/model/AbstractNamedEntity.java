package ru.petrov.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public abstract class AbstractNamedEntity extends AbstractEntity {
    @NotEmpty(message = "Name not might be empty")
    @Size(min = 3, max = 60, message = "Name might contain 3-60 char")
    protected String name;

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
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
        return String.format("%s. ID: %s. Name: %s.", getClass().getSimpleName(), id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractNamedEntity that = (AbstractNamedEntity) o;
        return name.equals(that.name) && id.equals(that.id);
    }

}
