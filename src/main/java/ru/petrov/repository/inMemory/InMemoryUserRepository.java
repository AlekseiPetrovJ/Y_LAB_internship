package ru.petrov.repository.inMemory;

import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.NotFoundException;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private List<User> users = new ArrayList<>();

    public InMemoryUserRepository() {
    }

    @Override
    public Optional<User> save(User user) {
        if (user.isNew()) {
            user.setUuid(UUID.randomUUID());
            users.add(user);
        } else {
            if (users.contains(user)) {
                users.replaceAll(user1 -> user1 = user);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(user);
    }

    @Override
    public boolean delete(UUID uuid) {
        if (get(uuid).isEmpty()) {
            throw new NotFoundException("Not found entity with uuid: " + uuid);
        } else {
            return users.remove(get(uuid).get());
        }


    }

    @Override
    public Optional<User> get(UUID uuid) {
        return users.stream().filter(user -> user.getUuid().equals(uuid)).findAny();
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
