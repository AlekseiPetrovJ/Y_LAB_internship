package ru.petrov.repository.jdbc;

import ru.petrov.annotations.Loggable;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcUserRepository implements UserRepository {
    @Override
    public Optional<User> save(User user) {
        String query;
        if (user.isNew()) {
            query = "INSERT INTO person (name, password, role_id)\n" +
                    "VALUES (?, ?, ?);";
        } else {
            query = """
                    UPDATE person SET\s
                    name = ?,\s
                    password=?,\s
                    role_id = ?,\s
                    registered = ?
                    WHERE person_id=?;""";
        }

        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRole().getRoleId());
            if (!user.isNew()) {
                preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getRegistered()));
                preparedStatement.setInt(5, user.getId());
            }
            if (preparedStatement.executeUpdate() > 0) {
                return get(user.getName());
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from person where person_id=?";
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<User> get(Integer id) {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id " +
                "where person_id=?";
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
                return Optional.of(new User(id, name, password, role));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    @Loggable
    public Optional<User> get(String name) {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id " +
                "where name=?";
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer personId = resultSet.getInt("person_id");
                String password = resultSet.getString("password");
                Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
                return Optional.of(new User(personId, name, password, role));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id ";
        try (Connection con = JdbcConnector.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                Integer personId = resultSet.getInt("person_id");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
                users.add(new User(personId, name, password, role));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return users;
    }
}
