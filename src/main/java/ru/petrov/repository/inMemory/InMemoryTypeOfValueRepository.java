package ru.petrov.repository.inMemory;

import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.TypeOfValueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.petrov.model.AbstractEntity.START_SEQ;

public class InMemoryTypeOfValueRepository implements TypeOfValueRepository {
    private final List<TypeOfValue> typeOfValues = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(START_SEQ);

    public InMemoryTypeOfValueRepository() {
    }

    @Override
    public Optional<TypeOfValue> save(TypeOfValue typeOfValue) {
        if (typeOfValue.isNew() && get(typeOfValue.getName()).isEmpty()) {
            typeOfValue.setId(counter.incrementAndGet());
            typeOfValues.add(typeOfValue);
            return get(typeOfValue.getId());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Integer id) {
        return typeOfValues.remove(get(id).
                orElse(null));
    }

    @Override
    public Optional<TypeOfValue> get(Integer id) {
        return typeOfValues.stream().filter(type -> type.getId().equals(id)).findAny();
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
