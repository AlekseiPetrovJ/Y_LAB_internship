package ru.petrov.repository.jdbc;

import ru.petrov.model.Measurement;
import ru.petrov.model.TypeOfValue;
import ru.petrov.model.User;
import ru.petrov.repository.MeasurementRepository;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;

import java.sql.*;
import java.util.*;

public class JdbcMeasurementRepository implements MeasurementRepository {
    private final UserRepository userRepository;
    private final TypeOfValueRepository typeOfValueRepository;

    public JdbcMeasurementRepository(UserRepository userRepository, TypeOfValueRepository typeOfValueRepository) {
        this.userRepository = userRepository;
        this.typeOfValueRepository = typeOfValueRepository;
    }

    @Override
    public Optional<Measurement> save(Measurement measurement, UUID userUuid) {
        String query;
        if (measurement.isNew()) {
            query = "INSERT INTO measurement (person_uuid, measurement_value, type_uuid, reg_year, reg_month) " +
                    "VALUES (UUID(?), ?, UUID(?), ?, ?);";

            try (Connection con = getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(query)) {

                User user = userRepository.get(userUuid).get();
                preparedStatement.setString(1, userUuid.toString());
                preparedStatement.setDouble(2, measurement.getValue());
                preparedStatement.setString(3, measurement.getTypeOfValue().getUuid().toString());
                preparedStatement.setInt(4, measurement.getYear());
                preparedStatement.setInt(5, measurement.getMonth());
                if (preparedStatement.executeUpdate() > 0) {
                    measurement.setUser(user);
                    return Optional.of(measurement);
                }

            } catch (SQLException e) {
                //TODO перенести в лог
                System.out.println("SQL exception: " + e.getMessage());
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public List<Measurement> getLatest(UUID userUuid) {
        String selectSql = "SELECT * " +
                "FROM ( " +
                "    SELECT measurement_uuid, measurement_value, type_uuid, reg_year, reg_month," +
                "           ROW_NUMBER() OVER (PARTITION BY type_uuid ORDER BY reg_year DESC, reg_month DESC) AS rn " +
                "    FROM measurement WHERE person_uuid=UUID(?) " +
                ") AS subquery " +
                "WHERE rn = 1;";

        List<Measurement> measurementsLatest = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, userUuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID measurementUuid = UUID.fromString(resultSet.getString("measurement_uuid"));
                UUID typeUuid = UUID.fromString(resultSet.getString("type_uuid"));
                int year = resultSet.getInt("reg_year");
                int month = resultSet.getInt("reg_month");
                User user = userRepository.get(userUuid).get();
                TypeOfValue type = typeOfValueRepository.get(typeUuid).get();
                double measurement_value = resultSet.getDouble("measurement_value");
                measurementsLatest.add(new Measurement(measurementUuid, type, year, month, user, measurement_value));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
            return measurementsLatest;
        }
        return measurementsLatest;
    }

    @Override
    public List<Measurement> getByMonth(int year, int month, UUID userUuid) {
        String selectSql = "SELECT measurement_uuid, measurement_value, measurement.type_uuid " +
                "FROM measurement " +
                "WHERE reg_year=? AND reg_month=? AND measurement.person_uuid=UUID(?)";
        List<Measurement> measurementsMonth = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setString(3, userUuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID measurementUuid = UUID.fromString(resultSet.getString("measurement_uuid"));
                double measurement_value = resultSet.getDouble("measurement_value");
                UUID typeUuid = UUID.fromString(resultSet.getString("type_uuid"));
                User user = userRepository.get(userUuid).get();
                TypeOfValue type = typeOfValueRepository.get(typeUuid).get();
                measurementsMonth.add(new Measurement(measurementUuid, type, year, month, user, measurement_value));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
            return measurementsMonth;
        }
        return measurementsMonth;
    }

    @Override
    public List<Measurement> getAll(UUID userUuid) {
        String selectSql = "SELECT measurement_uuid, measurement_value, reg_year, reg_month, measurement.type_uuid " +
                "FROM measurement  " +
                "WHERE measurement.person_uuid=UUID(?) " +
                "ORDER BY reg_year, reg_month";
        List<Measurement> measurementsAll = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setString(1, userUuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID measurementUuid = UUID.fromString(resultSet.getString("measurement_uuid"));
                UUID typeUuid = UUID.fromString(resultSet.getString("type_uuid"));
                int year = resultSet.getInt("reg_year");
                int month = resultSet.getInt("reg_month");
                User user = userRepository.get(userUuid).get();
                TypeOfValue type = typeOfValueRepository.get(typeUuid).get();
                double measurement_value = resultSet.getDouble("measurement_value");
                measurementsAll.add(new Measurement(measurementUuid, type, year, month, user, measurement_value));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
            return measurementsAll;
        }
        return measurementsAll;
    }

    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("db/database");
        String url = resource.getString("url");
        String db_user = resource.getString("db_user");
        String pass = resource.getString("password");
        return DriverManager.getConnection(url, db_user, pass);
    }
}
