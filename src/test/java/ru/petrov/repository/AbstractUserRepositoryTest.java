package ru.petrov.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import ru.petrov.model.Role;
import ru.petrov.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractUserRepositoryTest {
    public static UserRepository userRepository;

    static User user;
    static User userNew;
    static User admin;

    public static void setUserRepository(UserRepository userRepository) {
        AbstractUserRepositoryTest.userRepository = userRepository;
    }

    @BeforeEach
    void set() {
        user = new User("User", "password", Role.USER);
        userNew = new User("UserNew", "passwordnew", Role.USER);
        admin = new User("Admin", "password", Role.ADMIN);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Добавление нового пользователя.")
    void save() {
        user.setUuid(userRepository.save(user).get().getUuid());
        assertEquals(Optional.of(user), userRepository.get(user.getUuid()));
        userRepository.delete(user.getUuid());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Изменение существующего пользователя.")
    void update() {
        Optional<User> actualUser = userRepository.save(user);
        UUID uuidTemp = actualUser.get().getUuid();
        userNew.setUuid(uuidTemp);
        userRepository.save(userNew);
        assertEquals(Optional.of(userNew), userRepository.get(uuidTemp));
        userRepository.delete(uuidTemp);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Проверка входных данных")
    void updateNotFound() {
        assertEquals(Optional.empty(), userRepository.get(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление существующего пользователя.")
    void delete() {
        admin.setUuid(userRepository.save(admin).get().getUuid());
        assertTrue(userRepository.delete(admin.getUuid()));
        assertEquals(Optional.empty(), userRepository.get(admin.getUuid()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление не существующего пользователя")
    public void deleteNotFound() {
        Assertions.assertFalse(userRepository.delete(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение существующего пользователя")
    void get() {
        admin.setUuid(userRepository.save(admin).get().getUuid());
        assertEquals(Optional.of(admin), userRepository.get(admin.getUuid()));
        userRepository.delete(admin.getUuid());

    }


    @org.junit.jupiter.api.Test
    @DisplayName("Получение не существущего пользователя")
    void getNotFound() {
        assertEquals(Optional.empty(), userRepository.get(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение списка всех пользователей")
    void getAll() {
        userNew.setUuid(userRepository.save(userNew).get().getUuid());
        admin.setUuid(userRepository.save(admin).get().getUuid());
        user.setUuid(userRepository.save(user).get().getUuid());
        List<User> users = Arrays.asList(user, userNew, admin);
        assertTrue(users.containsAll(userRepository.getAll()));
        userRepository.delete(userNew.getUuid());
        userRepository.delete(admin.getUuid());
        userRepository.delete(user.getUuid());

    }
}