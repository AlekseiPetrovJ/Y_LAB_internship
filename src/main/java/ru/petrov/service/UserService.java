package ru.petrov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(int id) {
        Optional<User> foundUser = userRepository.get(id);
        return foundUser.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<User> get(String name) {
        return userRepository.get(name);
    }

    public Optional<User> save(User user) {
        user.setRegistered(LocalDateTime.now());
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }
}
