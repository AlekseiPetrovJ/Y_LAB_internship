package ru.petrov.repository;

import ru.petrov.model.Measurement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeasurementRepository {
    /**
     * @return empty Optional when attempt update or measurement does not belong to userId
     */
    Optional<Measurement> save(Measurement measurement, UUID userUuid);

    List<Measurement> getLatest(UUID userUuid);

    List<Measurement> getByMonth(int year, int month, UUID userUuid);

    /**
     * @return List<Measurement> ordered by Measurement's date
     */
    List<Measurement> getAll(UUID userUuid);
}
