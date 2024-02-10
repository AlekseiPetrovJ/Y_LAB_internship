package ru.petrov.repository.jdbc;

import ru.petrov.model.Measurement;
import ru.petrov.model.TypeOfValue;
import ru.petrov.model.User;
import ru.petrov.repository.MeasurementRepository;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;

import java.sql.*;
import java.util.*;

public class JdbcMeasurementRepository extends  AbstractJdbc implements MeasurementRepository {
    private final UserRepository userRepository;
    private final TypeOfValueRepository typeOfValueRepository;

    public JdbcMeasurementRepository(UserRepository userRepository, TypeOfValueRepository typeOfValueRepository) {
        this.userRepository = userRepository;
        this.typeOfValueRepository = typeOfValueRepository;
    }

    @Override
    public Optional<Measurement> save(Measurement measurement, Integer userId) {
        String query;
        if (measurement.isNew()) {
            query = "INSERT INTO measurement (person_id, measurement_value, type_id, reg_year, reg_month) " +
                    "VALUES (?, ?, ?, ?, ?);";

            try (Connection con = getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(query)) {

                User user = userRepository.get(userId).get();
                preparedStatement.setInt(1, userId);
                preparedStatement.setDouble(2, measurement.getValue());
                preparedStatement.setInt(3, measurement.getTypeOfValue().getId());
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
    public List<Measurement> getLatest(Integer userId) {
        String selectSql = "SELECT * " +
                "FROM ( " +
                "    SELECT measurement_id, measurement_value, type_id, reg_year, reg_month," +
                "           ROW_NUMBER() OVER (PARTITION BY type_id ORDER BY reg_year DESC, reg_month DESC) AS rn " +
                "    FROM measurement WHERE person_id=? " +
                ") AS subquery " +
                "WHERE rn = 1;";

        List<Measurement> measurementsLatest = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer measurementId = resultSet.getInt("measurement_id");
                Integer typeId = resultSet.getInt("type_id");
                int year = resultSet.getInt("reg_year");
                int month = resultSet.getInt("reg_month");
                User user = userRepository.get(userId).get();
                TypeOfValue type = typeOfValueRepository.get(typeId).get();
                double measurement_value = resultSet.getDouble("measurement_value");
                measurementsLatest.add(new Measurement(measurementId, type, year, month, user, measurement_value));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
            return measurementsLatest;
        }
        return measurementsLatest;
    }

    @Override
    public List<Measurement> getByMonth(int year, int month, Integer userId) {
        String selectSql = "SELECT measurement_id, measurement_value, measurement.type_id " +
                "FROM measurement " +
                "WHERE reg_year=? AND reg_month=? AND measurement.person_id=?";
        List<Measurement> measurementsMonth = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer measurementId = resultSet.getInt("measurement_id");
                double measurement_value = resultSet.getDouble("measurement_value");
                Integer typeId = resultSet.getInt("type_id");
                User user = userRepository.get(userId).get();
                TypeOfValue type = typeOfValueRepository.get(typeId).get();
                measurementsMonth.add(new Measurement(measurementId, type, year, month, user, measurement_value));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
            return measurementsMonth;
        }
        return measurementsMonth;
    }

    @Override
    public List<Measurement> getAll(Integer userId) {
        String selectSql = "SELECT measurement_id, measurement_value, reg_year, reg_month, measurement.type_id " +
                "FROM measurement  " +
                "WHERE measurement.person_id=? " +
                "ORDER BY reg_year, reg_month";
        List<Measurement> measurementsAll = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer measurementId = resultSet.getInt("measurement_id");
                Integer typeId = resultSet.getInt("type_id");
                int year = resultSet.getInt("reg_year");
                int month = resultSet.getInt("reg_month");
                User user = userRepository.get(userId).get();
                TypeOfValue type = typeOfValueRepository.get(typeId).get();
                double measurement_value = resultSet.getDouble("measurement_value");
                measurementsAll.add(new Measurement(measurementId, type, year, month, user, measurement_value));
            }
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("SQL exception: " + e.getMessage());
            return measurementsAll;
        }
        return measurementsAll;
    }
}
