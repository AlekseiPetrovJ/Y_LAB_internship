package ru.petrov.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.petrov.model.Measurement;
import ru.petrov.model.User;
import ru.petrov.repository.MeasurementRepository;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.mapper.MeasurementRowMapper;

import java.util.List;
import java.util.Optional;

public class JdbcMeasurementRepository implements MeasurementRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MeasurementRowMapper measurementRowMapper;
    private final UserRepository userRepository;

    @Autowired
    public JdbcMeasurementRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, TypeOfValueRepository typeRepository, MeasurementRowMapper measurementRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.measurementRowMapper = measurementRowMapper;
    }

    @Override
    public Optional<Measurement> save(Measurement measurement, Integer userId) {
        if (measurement.isNew()) {
            String query = "INSERT INTO measurement (person_id, measurement_value, type_id, reg_year, reg_month) " +
                    "VALUES (?, ?, ?, ?, ?);";
            if (jdbcTemplate.update(query,
                    userId,
                    measurement.getValue(),
                    measurement.getTypeOfValue().getId(),
                    measurement.getYear(),
                    measurement.getMonth()) > 0) {
                User user = userRepository.get(userId).get();
                measurement.setUser(user);
                return Optional.of(measurement);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Measurement> getLatest(Integer userId) {
        String selectSql = "SELECT * " +
                "FROM ( " +
                "    SELECT measurement_id, measurement_value, type_id, reg_year, reg_month," +
                "           ROW_NUMBER() OVER (PARTITION BY type_id ORDER BY reg_year DESC, reg_month DESC) AS rn " +
                "    FROM measurement WHERE person_id=? " +
                ") AS subquery " +
                "WHERE rn = 1;";
        return jdbcTemplate.query(selectSql, new Object[]{userId}, measurementRowMapper);
    }

    @Override
    public List<Measurement> getByMonth(int year, int month, Integer userId) {
        String selectSql = "SELECT measurement_id, measurement_value, measurement.type_id " +
                "FROM measurement " +
                "WHERE reg_year=? AND reg_month=? AND measurement.person_id=?";
        return jdbcTemplate.query(selectSql, new Object[]{year, month, userId}, measurementRowMapper);
    }

    @Override
    public List<Measurement> getAll(Integer userId) {
        String selectSql = "SELECT measurement_id, measurement_value, reg_year, reg_month, measurement.type_id " +
                "FROM measurement  " +
                "WHERE measurement.person_id=? " +
                "ORDER BY reg_year, reg_month";
        return jdbcTemplate.query(selectSql, new Object[]{userId}, measurementRowMapper);
    }
}
