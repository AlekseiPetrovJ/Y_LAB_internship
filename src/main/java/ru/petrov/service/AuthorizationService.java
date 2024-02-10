package ru.petrov.service;

import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

import java.util.Optional;

public class AuthorizationService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
    *@return empty Optional if User with name-password not found in repository
     */
    public Optional<User> authorization(String name, String password) {
        if (userRepository.get(name).isPresent() &&
                userRepository.get(name).get().getPassword().equals(password)) {
            return userRepository.get(name);
        }
        return Optional.empty();
    }
}
