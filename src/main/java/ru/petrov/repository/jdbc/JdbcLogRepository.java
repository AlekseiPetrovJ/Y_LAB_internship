package ru.petrov.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.petrov.model.Log;
import ru.petrov.model.LogLevel;
import ru.petrov.repository.LogRepository;
import ru.petrov.util.mapper.LogRowMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLogRepository implements LogRepository {
    private final JdbcTemplate jdbcTemplate;
    private final LogRowMapper logRowMapper;

    @Autowired
    public JdbcLogRepository(JdbcTemplate jdbcTemplate, LogRowMapper logRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.logRowMapper = logRowMapper;
    }

    @Override
    public Optional<Log> save(Log log) {
        String query = "INSERT INTO application_log (level_id, person_id, registered, log_value, duration) " +
                "VALUES (?, ?, ?, ?, ?);";
        if (jdbcTemplate.update(query,
                log.getLevel().getLevelId(),
                log.getUser().getId(),
                Timestamp.valueOf(log.getRegistered()),
                log.getLogValue(),
                log.getDuration()) > 0) {
            return Optional.of(log);
        }
        return Optional.empty();
    }

    @Override
    public List<Log> get(Integer userId) {
        String selectSql =
                "SELECT log_id, registered, log_level, person_id, log_value, duration " +
                        "FROM application_log  " +
                        "LEFT JOIN log_level ON application_log.level_id=log_level.level_id " +
                        "WHERE application_log.person_id=? " +
                        "ORDER BY registered";
        return jdbcTemplate.query(selectSql, new Object[]{userId}, logRowMapper);
    }

    @Override
    public List<Log> getByLevel(LogLevel level, Integer userId) {
        String selectSql =
                "SELECT log_id, registered, level_id, person_id, log_value, duration " +
                        "FROM application_log " +
                        "WHERE level_id=? AND application_log.person_id=? " +
                        "ORDER BY registered";
        return jdbcTemplate.query(selectSql, new Object[]{level.getLevelId(), userId}, logRowMapper);
    }
}