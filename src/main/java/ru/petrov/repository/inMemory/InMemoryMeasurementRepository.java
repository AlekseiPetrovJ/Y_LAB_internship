package ru.petrov.repository.inMemory;

import ru.petrov.model.Measurement;
import ru.petrov.repository.MeasurementRepository;
import ru.petrov.util.NotFoundException;
import ru.petrov.util.NotUniqueValueException;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

public class InMemoryMeasurementRepository implements MeasurementRepository {
    private final List<Measurement> measurements = new ArrayList<>();
    private InMemoryUserRepository userRepository;

    public InMemoryMeasurementRepository() {
    }

    public InMemoryMeasurementRepository(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Measurement> save(Measurement measurement, UUID userUuid) {
        if (measurement.isNew()) {
            if (measurements.stream().anyMatch(isValueOfThisType(measurement))) {
                throw new NotUniqueValueException("there is already a value of this type in this month");
            }
            measurement.setUser(userRepository.get(userUuid)
                    .orElseThrow(() -> new NotFoundException("Not found entity with uuid: " + userUuid)));
            measurement.setUuid(UUID.randomUUID());
            measurements.add(measurement);
            return Optional.of(measurement);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Measurement> getLatest(UUID userUuid) {
        return new ArrayList<>(measurements.stream()
                .filter(measurement -> measurement.getUser().getUuid().equals(userUuid))
                .collect(toMap(Measurement::getTypeOfValue, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Measurement::getRegistered)))).values());
    }

    @Override
    public List<Measurement> getByMonth(LocalDate date, UUID userUuid) {
        return measurements.stream()
                .filter(isValueInThisPeriod(date, userUuid))
                .collect(toList());
    }

    private static Predicate<Measurement> isValueInThisPeriod(LocalDate date, UUID userUuid) {
        return measurement -> (measurement.getRegistered().getMonth().equals(date.getMonth())
                && measurement.getRegistered().getYear() == date.getYear()
                && measurement.getUser().getUuid().equals(userUuid));
    }

    private static Predicate<Measurement> isValueOfThisType(Measurement m1) {
        return m2 -> (m2.getRegistered().getMonth().equals(m1.getRegistered().getMonth())
                && m2.getRegistered().getYear() == m1.getRegistered().getYear()
                && m2.getUser().getUuid().equals(m1.getUser().getUuid())
                && m2.getTypeOfValue().equals(m1.getTypeOfValue()));
    }

    @Override
    public List<Measurement> getAll(UUID userUuid) {
        return measurements.stream()
                .filter(measurement -> measurement.getUser().getUuid().equals(userUuid))
                .sorted(Comparator.comparing(Measurement::getRegistered))
                .collect(toList());
    }
}
