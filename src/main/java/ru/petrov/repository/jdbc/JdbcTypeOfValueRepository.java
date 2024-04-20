package ru.petrov.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.util.mapper.TypeOfValueRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTypeOfValueRepository implements TypeOfValueRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTypeOfValueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<TypeOfValue> save(TypeOfValue type) {
        if (type.isNew()) {
            String query = "INSERT INTO type_of_value (name, unit_of_measurement)\n" +
                    "VALUES (?, ?);";
            if (jdbcTemplate.update(query, type.getName(), type.getUnitOfMeasurement()) > 0) {
                return get(type.getName());
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM type_of_value WHERE type_id=?";
        return jdbcTemplate.update(query, new Object[]{id}) > 0;
    }

    @Override
    public Optional<TypeOfValue> get(Integer id) {
        String selectSql = "SELECT * FROM type_of_value WHERE type_id=?";
        return jdbcTemplate.query(selectSql, new Object[]{id}, new TypeOfValueRowMapper())
                .stream()
                .findAny();
    }

    @Override
    public Optional<TypeOfValue> get(String name) {
        String selectSql = "SELECT * FROM type_of_value WHERE name=?";
        return jdbcTemplate.query(selectSql, new Object[]{name}, new TypeOfValueRowMapper())
                .stream()
                .findAny();
    }

    @Override
    public List<TypeOfValue> getAll() {
        String selectSql = "SELECT * FROM type_of_value";
        return jdbcTemplate.query(selectSql, new TypeOfValueRowMapper());
    }
}
