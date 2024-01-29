package ru.petrov.repository;

import ru.petrov.model.TypeOfValue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeOfValueRepository {
    //empty Optional if not new
    Optional<TypeOfValue> save(TypeOfValue typeOfValue);

    // false if not found
    boolean delete(UUID uuid);

    //empty Optional if not found
    Optional<TypeOfValue> get(UUID uuid);

    Optional<TypeOfValue> get(String name);

    List<TypeOfValue> getAll();
}
