package ru.petrov.util.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.petrov.model.Log;
import ru.petrov.model.LogLevel;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LogMapper implements RowMapper<Log> {
    public LogMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private  final UserRepository userRepository;

    @Override
    public Log mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        LocalDateTime registered = resultSet.getTimestamp("registered").toLocalDateTime();
        LogLevel level = Enum.valueOf(LogLevel.class, resultSet.getString("log_level"));
        String logValue = resultSet.getString("log_value");
        int userId = resultSet.getInt("person_id");
        User user = userRepository.get(userId).orElse(null);
        return new Log(registered, level, user, logValue);
    }
}