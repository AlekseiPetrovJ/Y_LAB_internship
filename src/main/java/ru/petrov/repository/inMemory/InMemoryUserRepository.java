package ru.petrov.repository.inMemory;

import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.petrov.model.AbstractEntity.START_SEQ;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(START_SEQ);

    public InMemoryUserRepository() {
    }

    @Override
    public Optional<User> save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            users.add(user);
        } else {
            if (get(user.getId()).isPresent()) {
                users.replaceAll(user1 -> user1 = user);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(user);
    }

    @Override
    public boolean delete(Integer id) {
        return users.remove(get(id).
                orElse(null));
    }

    @Override
    public Optional<User> get(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findAny();
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
