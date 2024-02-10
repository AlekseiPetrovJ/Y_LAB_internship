package ru.petrov.repository;

import ru.petrov.model.TypeOfValue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TypeOfValueRepository {
    /**
     * @return empty Optional if not new
     */
    Optional<TypeOfValue> save(TypeOfValue typeOfValue);

    /**
     * @return false if not found
     */
    boolean delete(UUID uuid);

    /**
     * @return empty Optional if not found
     */
    Optional<TypeOfValue> get(UUID uuid);

    /**
     * @return empty Optional if not found
     */
    Optional<TypeOfValue> get(String name);

    List<TypeOfValue> getAll();
}
