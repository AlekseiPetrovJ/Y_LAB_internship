package ru.petrov.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import ru.petrov.model.TypeOfValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractTypeOfValueRepositoryTest {
    public static TypeOfValueRepository typeOfValueRepository;

    static TypeOfValue hotWater;
    static TypeOfValue coldWater;
    static TypeOfValue coldWaterNew;


    public static void setTypeOfValueRepository(TypeOfValueRepository typeOfValueRepository) {
        AbstractTypeOfValueRepositoryTest.typeOfValueRepository = typeOfValueRepository;
    }

    @BeforeEach
    void set() {
        hotWater = new TypeOfValue("hot water", "m3");
        coldWater = new TypeOfValue("cold water", "m3");
        coldWaterNew = new TypeOfValue("very cold water", "m3");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Добавление нового типа измерения.")
    void save() {
        hotWater.setUuid(typeOfValueRepository.save(hotWater).get().getUuid());
        assertEquals(Optional.of(hotWater), typeOfValueRepository.get(hotWater.getUuid()));
        typeOfValueRepository.delete(hotWater.getUuid());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Изменение существующего типа измерения.")
    void update() {
        Optional<TypeOfValue> actualType = typeOfValueRepository.save(coldWater);
        UUID uuidTemp = actualType.get().getUuid();
        coldWaterNew.setUuid(uuidTemp);
        Optional<TypeOfValue> actual = typeOfValueRepository.save(coldWaterNew);
        assertEquals(Optional.empty(), actual);
        typeOfValueRepository.delete(uuidTemp);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Попытка получить отсутствующие данные.")
    void updateNotFound() {
        assertEquals(Optional.empty(), typeOfValueRepository.get(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление существующего типа измерения.")
    void delete() {
        hotWater.setUuid(typeOfValueRepository.save(hotWater).get().getUuid());
        assertTrue(typeOfValueRepository.delete(hotWater.getUuid()));
        assertEquals(Optional.empty(), typeOfValueRepository.get(hotWater.getUuid()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление не существующего типа измерения.")
    public void deleteNotFound() {
        Assertions.assertFalse(typeOfValueRepository.delete(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение существующего типа измерения")
    void get() {
        hotWater.setUuid(typeOfValueRepository.save(hotWater).get().getUuid());
        assertEquals(Optional.of(hotWater), typeOfValueRepository.get(hotWater.getUuid()));
        typeOfValueRepository.delete(hotWater.getUuid());
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Получение не существущего типа измерения")
    void getNotFound() {
        assertEquals(Optional.empty(), typeOfValueRepository.get(UUID.randomUUID()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение списка всех пользователей")
    void getAll() {
        hotWater.setUuid(typeOfValueRepository.save(hotWater).get().getUuid());
        coldWater.setUuid(typeOfValueRepository.save(coldWater).get().getUuid());
        List<TypeOfValue> users = Arrays.asList(hotWater, coldWater);
        assertTrue(users.containsAll(typeOfValueRepository.getAll()));
        typeOfValueRepository.delete(hotWater.getUuid());
        typeOfValueRepository.delete(coldWater.getUuid());

    }
}