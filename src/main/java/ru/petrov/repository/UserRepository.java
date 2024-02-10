package ru.petrov.repository;

import ru.petrov.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    /**
     * @return empty Optional if not found, when update
     */
    Optional<User> save(User user);

    /**
     * @return false if not found
     */
    boolean delete(Integer id);

    /**
     * @return empty Optional if not found
     */
    Optional<User> get(Integer id);

    /**
     * @return empty Optional if not found
     */
    Optional<User> get(String name);

    List<User> getAll();
}
