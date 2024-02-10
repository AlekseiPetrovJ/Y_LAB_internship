package ru.petrov.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import ru.petrov.InitializationDb;
import ru.petrov.model.TypeOfValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        InitializationDb.migrationForTest();
        hotWater = new TypeOfValue("hot water", "m3");
        coldWater = new TypeOfValue("cold water", "m3");
        coldWaterNew = new TypeOfValue("very cold water", "m3");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Добавление нового типа измерения.")
    void save() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        assertEquals(Optional.of(hotWater), typeOfValueRepository.get(hotWater.getId()));
        typeOfValueRepository.delete(hotWater.getId());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Изменение существующего типа измерения.")
    void update() {
        Optional<TypeOfValue> actualType = typeOfValueRepository.save(coldWater);
        Integer idTemp = actualType.get().getId();
        coldWaterNew.setId(idTemp);
        Optional<TypeOfValue> actual = typeOfValueRepository.save(coldWaterNew);
        assertEquals(Optional.empty(), actual);
        typeOfValueRepository.delete(idTemp);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Попытка получить отсутствующие данные.")
    void updateNotFound() {
        assertEquals(Optional.empty(), typeOfValueRepository.get(500));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление существующего типа измерения.")
    void delete() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        assertTrue(typeOfValueRepository.delete(hotWater.getId()));
        assertEquals(Optional.empty(), typeOfValueRepository.get(hotWater.getId()));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Удаление не существующего типа измерения.")
    public void deleteNotFound() {
        Assertions.assertFalse(typeOfValueRepository.delete(500));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Получение существующего типа измерения")
    void get() {
        hotWater.setId(typeOfValueRepository.save(hotWater).get().getId());
        assertEquals(Optional.of(hotWater), typeOfValueRepository.get(hotWater.getId()));
        typeOfValueRepository.delete(hotWater.getId());
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Получение не существущего типа измерения")
    void getNotFound() {
        assertEquals(Optional.empty(), typeOfValueRepository.get(500));
    }

    @org.junit.jupiter.api.Test
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