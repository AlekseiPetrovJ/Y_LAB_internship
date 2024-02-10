package ru.petrov.repository;

import ru.petrov.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    /**
     * @return empty Optional if not found, when update
     */
    Optional<User> save(User user);

    /**
     * @return false if not found
     */
    boolean delete(UUID uuid);

    /**
     * @return empty Optional if not found
     */
    Optional<User> get(UUID uuid);

    /**
     * @return empty Optional if not found
     */
    Optional<User> get(String name);

    List<User> getAll();
}
