package ru.petrov.repository;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.jdbc.JdbcUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class UserRepositoryTest {

    private static UserRepository userRepository;

    User user;
    User userNew;
    User admin;

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:15.5");

    @BeforeAll
    public static void startContainer() {
        postgresqlContainer.start();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresqlContainer.getDriverClassName());
        dataSource.setUrl(postgresqlContainer.getJdbcUrl());
        dataSource.setUsername(postgresqlContainer.getUsername());
        dataSource.setPassword(postgresqlContainer.getPassword());
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/changelog-for-test.xml");
        liquibase.setDataSource(dataSource);
        try {
            liquibase.afterPropertiesSet();
            System.out.println("Changelog applied successfully.");
        } catch (LiquibaseException e) {
            System.err.println("Error applying changelog: " + e.getMessage());
        }
        userRepository = new JdbcUserRepository(new JdbcTemplate(dataSource));
    }

    @AfterAll
    public static void stopContainer() {
        postgresqlContainer.stop();
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
    @DisplayName("Удаление не существующего пользователя")
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
    @DisplayName("Получение не существущего пользователя")
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