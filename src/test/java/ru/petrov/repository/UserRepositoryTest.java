package ru.petrov.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.jdbc.JdbcUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryTest extends AbstractRepositoryTest{

    private static UserRepository userRepository;

    User user;
    User userNew;
    User admin;

    @BeforeAll
    public static void setRepository() {
        userRepository = new JdbcUserRepository(new JdbcTemplate(dataSource));
    }

    @BeforeEach
    void set() {
        user = new User("User", "password", Role.ROLE_USER);
        userNew = new User("UserNew", "passwordnew", Role.ROLE_USER);
        admin = new User("Admin", "password", Role.ROLE_ADMIN);
    }

    @Test
    @DisplayName("Добавление нового пользователя.")
    void save() {
        user.setId(userRepository.save(user).get().getId());
        assertEquals(Optional.of(user), userRepository.get(user.getId()));
        userRepository.delete(user.getId());
    }

    @Test
    @DisplayName("Изменение существующего пользователя.")
    void update() {
        Optional<User> actualUser = userRepository.save(user);
        Integer idTemp = actualUser.get().getId();
        userNew.setId(idTemp);
        userRepository.save(userNew);
        assertEquals(Optional.of(userNew), userRepository.get(idTemp));
        userRepository.delete(idTemp);
    }

    @Test
    @DisplayName("Проверка входных данных")
    void updateNotFound() {
        assertEquals(Optional.empty(), userRepository.get(500));
    }

    @Test
    @DisplayName("Удаление существующего пользователя.")
    void delete() {
        admin.setId(userRepository.save(admin).get().getId());
        assertTrue(userRepository.delete(admin.getId()));
        assertEquals(Optional.empty(), userRepository.get(admin.getId()));
    }

    @Test
    @DisplayName("Удаление несуществующего пользователя")
    public void deleteNotFound() {
        Assertions.assertFalse(userRepository.delete(500));
    }

    @Test
    @DisplayName("Получение существующего пользователя")
    void get() {
        admin.setId(userRepository.save(admin).get().getId());
        assertEquals(Optional.of(admin), userRepository.get(admin.getId()));
        userRepository.delete(admin.getId());
    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    void getNotFound() {
        assertEquals(Optional.empty(), userRepository.get(500));
    }

    @Test
    @DisplayName("Получение списка всех пользователей")
    void getAll() {
        userNew.setId(userRepository.save(userNew).get().getId());
        admin.setId(userRepository.save(admin).get().getId());
        user.setId(userRepository.save(user).get().getId());
        List<User> users = Arrays.asList(user, userNew, admin);
        assertTrue(users.containsAll(userRepository.getAll()));
        userRepository.delete(userNew.getId());
        userRepository.delete(admin.getId());
        userRepository.delete(user.getId());
    }
}