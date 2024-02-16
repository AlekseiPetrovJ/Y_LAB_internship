package ru.petrov.repository.inMemory;

import ru.petrov.model.Measurement;
import ru.petrov.repository.MeasurementRepository;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.EntityNotFoundException;
import ru.petrov.util.NotUniqueValueException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static ru.petrov.model.AbstractEntity.START_SEQ;

public class InMemoryMeasurementRepository implements MeasurementRepository {
    private final List<Measurement> measurements = new ArrayList<>();
    private UserRepository userRepository;
    private final AtomicInteger counter = new AtomicInteger(START_SEQ);

    public InMemoryMeasurementRepository() {
    }

    public InMemoryMeasurementRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Measurement> save(Measurement measurement, Integer userId) {
        if (measurement.isNew()) {
            if (measurements.stream().anyMatch(isValueOfThisType(measurement))) {
                //TODO добавить логирование, убрать исключение и возвращать пустой Опционал
                throw new NotUniqueValueException("there is already a value of this type in this month");
            }
            measurement.setUser(userRepository.get(userId)
                    //TODO добавить логирование, убрать исключение и возвращать пустой Опционал
                    .orElseThrow(EntityNotFoundException::new));
            measurement.setId(counter.incrementAndGet());
            measurements.add(measurement);
            return Optional.of(measurement);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Measurement> getLatest(Integer userId) {
        return new ArrayList<>(measurements.stream()
                .filter(measurement -> measurement.getUser().getId().equals(userId))
                .collect(toMap(Measurement::getTypeOfValue, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Measurement::getYear)
                                .thenComparing(Measurement::getMonth)))).values());
    }

    @Override
    public List<Measurement> getByMonth(int year, int month, Integer userId) {
        return measurements.stream()
                .filter(isValueInThisPeriod(year, month, userId))
                .collect(toList());
    }

    private static Predicate<Measurement> isValueInThisPeriod(int year, int month, Integer userId) {
        return measurement -> (measurement.getMonth() == month
                && measurement.getYear() == year
                && measurement.getUser().getId().equals(userId));
    }

    private static Predicate<Measurement> isValueOfThisType(Measurement m1) {
        return m2 -> m2.getMonth() == m1.getMonth()
                && m2.getYear() == m1.getYear()
                && m2.getUser().getId().equals(m1.getUser().getId())
                && m2.getTypeOfValue().equals(m1.getTypeOfValue());
    }

    @Override
    public List<Measurement> getAll(Integer userId) {
        return measurements.stream()
                .filter(measurement -> measurement.getUser().getId().equals(userId))
                .sorted(Comparator.comparing(Measurement::getYear).thenComparing(Measurement::getMonth))
                .collect(toList());
    }
}
