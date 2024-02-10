package ru.petrov.repository;

import ru.petrov.model.Measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {
    /**
     * @return empty Optional when attempt update or measurement does not belong to userId
     */
    Optional<Measurement> save(Measurement measurement, Integer userId);

    List<Measurement> getLatest(Integer userId);

    List<Measurement> getByMonth(int year, int month, Integer userId);

    /**
     * @return List<Measurement> ordered by Measurement's date
     */
    List<Measurement> getAll(Integer userId);
}
