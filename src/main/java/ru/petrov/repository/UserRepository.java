package ru.petrov.repository;

import ru.petrov.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    //empty Optional if not found, when update
    Optional<User> save(User user);

    // false if not found
    boolean delete(UUID uuid);

    //empty Optional if not found
    Optional<User> get(UUID uuid);

    Optional<User> get(String name);


    List<User> getAll();
}
