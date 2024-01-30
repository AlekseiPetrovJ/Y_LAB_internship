package ru.petrov.repository.inMemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.NotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {
    static UserRepository userRepository = new InMemoryUserRepository();
    static User user;
    static User userNew;
    static User admin;

    @BeforeEach
    void set() {
        user = new User("User", "password", Role.USER);
        userNew = new User("UserNew", "passwordnew", Role.USER);
        admin = new User("Admin", "password", Role.ADMIN);

    }

    @org.junit.jupiter.api.Test
    @DisplayName("Добавление нового пользователя.")
    void save() {
        userRepository.save(user);
        assertEquals(Optional.of(user), userRepository.get(user.getUuid()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Изменение существующего пользователя.")
    void update() {
        userRepository.save(user);
        UUID uuidTemp = user.getUuid();
        userNew.setUuid(uuidTemp);
        userRepository.save(userNew);
        assertEquals(Optional.of(userNew), userRepository.get(uuidTemp));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Проверка входных данных")
    void updateNotFound() {
        assertEquals(Optional.empty(), userRepository.get(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление существующего пользователя.")
    void delete() {
        userRepository.save(admin);
        assertTrue(userRepository.delete(admin.getUuid()));
        assertEquals(Optional.empty(), userRepository.get(admin.getUuid()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление не существующего пользователя")
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> userRepository.delete(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение существующего пользователя")
    void get() {
        userRepository.save(admin);
        assertEquals(Optional.of(admin), userRepository.get(admin.getUuid()));
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Получение не существущего пользователя")
    void getNotFound() {
        assertEquals(Optional.empty(), userRepository.get(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение списка всех пользователей")
    void getAll() {
        userRepository.save(userNew);
        userRepository.save(admin);
        userRepository.save(user);
        List<User> users = Arrays.asList(user, userNew, admin);
        //Сравниваем содержимое вне зависимости от порядка элементов
        assertEquals(new HashSet<>(users)
                , new HashSet<>(userRepository.getAll()));
    }
}