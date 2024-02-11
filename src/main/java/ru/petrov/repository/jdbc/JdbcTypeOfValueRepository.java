package ru.petrov.repository.jdbc;

import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try (Connection con = JdbcConnector.getConnection();
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
    public boolean delete(Integer id) {
        String query = "DELETE FROM type_of_value WHERE type_id=?";
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
    public Optional<TypeOfValue> get(Integer id) {
        String selectSql = "SELECT * FROM type_of_value WHERE type_id=?";
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String unitOfMeasurement = resultSet.getString("unit_of_measurement");
                return Optional.of(new TypeOfValue(id, name, unitOfMeasurement));
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
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer typeId = resultSet.getInt("type_id");
                String typeName = resultSet.getString("name");
                String unitOfMeasurement = resultSet.getString("unit_of_measurement");
                return Optional.of(new TypeOfValue(typeId, typeName, unitOfMeasurement));
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
        try (Connection con = JdbcConnector.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                Integer typeId = resultSet.getInt("type_id");
                String name = resultSet.getString("name");
                String unitOfMeasurement = resultSet.getString("unit_of_measurement");
                types.add(new TypeOfValue(typeId, name, unitOfMeasurement));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
        }
        return types;
    }


}
