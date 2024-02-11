package ru.petrov.repository.jdbc;

import ru.petrov.model.Log;
import ru.petrov.model.LogLevel;
import ru.petrov.model.User;
import ru.petrov.repository.LogRepository;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.JdbcConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcLogRepository implements LogRepository {
    private final UserRepository userRepository;

    public JdbcLogRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Log> save(Log log) {
        if (userRepository.get(log.getUser().getId()).isPresent() && log.isNew()) {
            String query = "INSERT INTO application_log (level_id, person_id, registered, log_value) " +
                    "VALUES (?, ?, ?, ?);";

            try (Connection con = JdbcConnector.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(query)) {

                preparedStatement.setInt(1, log.getLevel().getLevelId());
                preparedStatement.setInt(2, log.getUser().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(log.getRegistered()));
                preparedStatement.setString(4, log.getLogValue());
                if (preparedStatement.executeUpdate() > 0) {
                    return Optional.of(log);
                }
            } catch (SQLException e) {
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Log> get(Integer userId) {
        List<Log> logs = new ArrayList<>();
        User user = userRepository.get(userId).orElse(null);
        if (user!=null){
            String selectSql =
                    "SELECT log_id, registered, log_level, person_id, log_value " +
                            "FROM application_log  " +
                            "LEFT JOIN log_level ON application_log.level_id=log_level.level_id " +
                            "WHERE application_log.person_id=? " +
                            "ORDER BY registered";
            try (Connection con = JdbcConnector.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    LocalDateTime registered = resultSet.getTimestamp("registered").toLocalDateTime();
                    LogLevel level = Enum.valueOf(LogLevel.class, resultSet.getString("log_level"));
                    String logValue = resultSet.getString("log_value");
                    logs.add(new Log(registered, level, user, logValue));
                }
            } catch (SQLException e) {
                //TODO перенести в лог
                System.out.println("SQL exception: " + e.getMessage());
            }
        }
        return logs;
    }

    @Override
    public List<Log> getByLevel(LogLevel level, Integer userId) {
        List<Log> logs = new ArrayList<>();
        User user = userRepository.get(userId).orElse(null);
        if (user!=null){
            String selectSql =
                    "SELECT log_id, registered, level_id, person_id, log_value " +
                            "FROM application_log " +
                            "WHERE application_log.person_id=? AND level_id=? " +
                            "ORDER BY registered";
            try (Connection con = JdbcConnector.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, level.getLevelId());

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    LocalDateTime registered = resultSet.getTimestamp("registered").toLocalDateTime();
                    String logValue = resultSet.getString("log_value");
                    logs.add(new Log(registered, level, user, logValue));
                }
            } catch (SQLException e) {
                //TODO перенести в лог
                System.out.println("SQL exception: " + e.getMessage());
            }
        }
        return logs;
    }
}
