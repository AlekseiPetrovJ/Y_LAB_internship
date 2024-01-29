package ru.petrov.repository.inMemory;

import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTypeOfValueRepository implements TypeOfValueRepository {
    private final List<TypeOfValue> typeOfValues = new ArrayList<>();

    public InMemoryTypeOfValueRepository() {
    }

    @Override
    public Optional<TypeOfValue> save(TypeOfValue typeOfValue) {
        if (typeOfValue.isNew() && get(typeOfValue.getName()).isEmpty()) {
            typeOfValue.setUuid(UUID.randomUUID());
            typeOfValues.add(typeOfValue);
            return get(typeOfValue.getUuid());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID uuid) {
        return typeOfValues.remove(get(uuid).
                orElseThrow(() -> new NotFoundException("Not found entity with uuid: " + uuid)));
    }

    @Override
    public Optional<TypeOfValue> get(UUID uuid) {
        return typeOfValues.stream().filter(type -> type.getUuid().equals(uuid)).findAny();
    }

    @Override
    public Optional<TypeOfValue> get(String name) {
        return typeOfValues.stream().filter(type -> type.getName().equals(name)).findAny();
    }

    @Override
    public List<TypeOfValue> getAll() {
        return typeOfValues;
    }
}
