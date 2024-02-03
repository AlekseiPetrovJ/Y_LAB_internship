package ru.petrov.repository.jdbc;

import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.TypeOfValueRepository;

import java.sql.*;
import java.util.*;

public class JdbcTypeOfValueRepository implements TypeOfValueRepository {
    @Override
    public Optional<TypeOfValue> save(TypeOfValue typeOfValue) {
        String query;
        if (typeOfValue.isNew()) {
            query = "INSERT INTO type_of_value (name, unit_of_measurement)\n" +
                    "VALUES (?, ?);";
        } else {
            return Optional.empty();
        }
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, typeOfValue.getName());
            preparedStatement.setString(2, typeOfValue.getUnitOfMeasurement());
            if (preparedStatement.executeUpdate() > 0) {
                return get(typeOfValue.getName());
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(UUID uuid) {
        String query = "DELETE FROM type_of_value WHERE type_uuid=UUID(?)";
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
    public Optional<TypeOfValue> get(UUID uuid) {
        String selectSql = "SELECT * FROM type_of_value WHERE type_uuid=UUID(?)";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String unitOfMeasurement = resultSet.getString("unit_of_measurement");
                return Optional.of(new TypeOfValue(uuid, name, unitOfMeasurement));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<TypeOfValue> get(String name) {
        String selectSql = "SELECT * FROM type_of_value WHERE name=?";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID typeUuid = UUID.fromString(resultSet.getString("type_uuid"));
                String typeName = resultSet.getString("name");
                String unitOfMeasurement = resultSet.getString("unit_of_measurement");
                return Optional.of(new TypeOfValue(typeUuid, typeName, unitOfMeasurement));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<TypeOfValue> getAll() {
        List<TypeOfValue> types = new ArrayList<>();
        String selectSql = "SELECT * FROM type_of_value";
        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                UUID typeUuid = UUID.fromString(resultSet.getString("type_uuid"));
                String name = resultSet.getString("name");
                String unitOfMeasurement = resultSet.getString("unit_of_measurement");
                types.add(new TypeOfValue(typeUuid, name, unitOfMeasurement));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return types;
    }

    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String db_user = resource.getString("db_user");
        String pass = resource.getString("password");
        return DriverManager.getConnection(url, db_user, pass);
    }
}
