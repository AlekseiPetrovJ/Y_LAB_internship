package ru.petrov.repository.inMemory;

import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    public InMemoryUserRepository() {
    }

    @Override
    public Optional<User> save(User user) {
        if (user.isNew()) {
            user.setUuid(UUID.randomUUID());
            users.add(user);
        } else {
            if (get(user.getUuid()).isPresent()) {
                users.replaceAll(user1 -> user1 = user);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(user);
    }

    @Override
    public boolean delete(UUID uuid) {
        return users.remove(get(uuid).
                orElse(null));
    }

    @Override
    public Optional<User> get(UUID uuid) {
        return users.stream().filter(user -> user.getUuid().equals(uuid)).findAny();
    }

    @Override
    public Optional<User> get(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findAny();
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
