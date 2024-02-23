package ru.petrov.repository;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.jdbc.JdbcTypeOfValueRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Testcontainers
public class TypeOfValueRepositoryTest {
    public static TypeOfValueRepository typeOfValueRepository;

    TypeOfValue hotWater;
    TypeOfValue coldWater;
    TypeOfValue coldWaterNew;
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
        typeOfValueRepository = new JdbcTypeOfValueRepository(new JdbcTemplate(dataSource));
    }

    @AfterAll
    public static void stopContainer() {
        postgresqlContainer.stop();
    }

    @BeforeEach
    void set() {
        hotWater = new TypeOfValue("hot water", "m3");
        coldWater = new TypeOfValue("cold water", "m3");
        coldWaterNew = new TypeOfValue("very cold water", "m3");
    }

    @Test
    @DisplayName("Добавление нового типа измерения.")
    void save() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        assertEquals(Optional.of(hotWater), typeOfValueRepository.get(hotWater.getId()));
        typeOfValueRepository.delete(hotWater.getId());
    }

    @Test
    @DisplayName("Изменение существующего типа измерения.")
    void update() {
        Optional<TypeOfValue> actualType = typeOfValueRepository.save(coldWater);
        Integer idTemp = actualType.get().getId();
        coldWaterNew.setId(idTemp);
        Optional<TypeOfValue> actual = typeOfValueRepository.save(coldWaterNew);
        assertEquals(Optional.empty(), actual);
        typeOfValueRepository.delete(idTemp);
    }

    @Test
    @DisplayName("Попытка получить отсутствующие данные.")
    void updateNotFound() {
        assertEquals(Optional.empty(), typeOfValueRepository.get(500));
    }

    @Test
    @DisplayName("Удаление существующего типа измерения.")
    void delete() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        assertTrue(typeOfValueRepository.delete(hotWater.getId()));
        assertEquals(Optional.empty(), typeOfValueRepository.get(hotWater.getId()));
    }

    @Test
    @DisplayName("Удаление несуществующего типа измерения.")
    public void deleteNotFound() {
        Assertions.assertFalse(typeOfValueRepository.delete(500));
    }

    @Test
    @DisplayName("Получение существующего типа измерения")
    void get() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        assertEquals(Optional.of(hotWater), typeOfValueRepository.get(hotWater.getId()));
        typeOfValueRepository.delete(hotWater.getId());
    }

    @Test
    @DisplayName("Получение несуществущего типа измерения")
    void getNotFound() {
        assertEquals(Optional.empty(), typeOfValueRepository.get(500));
    }

    @Test
    @DisplayName("Получение списка всех измерений")
    void getAll() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        coldWater.setId(typeOfValueRepository.save(coldWater).get().getId());
        List<TypeOfValue> users = Arrays.asList(hotWater, coldWater);
        assertTrue(users.containsAll(typeOfValueRepository.getAll()));
        typeOfValueRepository.delete(hotWater.getId());
        typeOfValueRepository.delete(coldWater.getId());
    }
}