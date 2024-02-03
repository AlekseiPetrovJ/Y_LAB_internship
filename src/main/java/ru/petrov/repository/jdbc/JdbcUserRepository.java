package ru.petrov.repository.jdbc;

import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

import java.sql.*;
import java.util.*;

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
                    WHERE person_uuid=uuid(?);""";
        }
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRole().getRoleId());
            if (!user.isNew()) {
                preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getRegistered()));
                preparedStatement.setString(5, user.getUuid().toString());
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
    public boolean delete(UUID uuid) {
        String query = "delete from person where person_uuid=uuid(?)";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, uuid.toString());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<User> get(UUID uuid) {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id " +
                "where person_uuid=uuid(?)";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
                return Optional.of(new User(uuid, name, password, role));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> get(String name) {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id " +
                "where name=?";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID person_uuid = UUID.fromString(resultSet.getString("person_uuid"));
                String password = resultSet.getString("password");
                Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
                return Optional.of(new User(person_uuid, name, password, role));
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
        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                UUID person_uuid = UUID.fromString(resultSet.getString("person_uuid"));
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
                users.add(new User(person_uuid, name, password, role));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return users;
    }

    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String db_user = resource.getString("db_user");
        String pass = resource.getString("password");
        return DriverManager.getConnection(url, db_user, pass);
    }
}
