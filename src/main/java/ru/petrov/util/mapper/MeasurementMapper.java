package ru.petrov.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import ru.petrov.model.Measurement;
import ru.petrov.model.TypeOfValue;
import ru.petrov.model.User;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MeasurementMapper implements RowMapper<Measurement> {
    private final UserRepository userRepository;
    private final TypeOfValueRepository typeOfValueRepository;

    @Autowired
    public MeasurementMapper(UserRepository userRepository, TypeOfValueRepository typeOfValueRepository) {
        this.userRepository = userRepository;
        this.typeOfValueRepository = typeOfValueRepository;
    }

    @Override
    public Measurement mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int measurementId = resultSet.getInt("measurement_id");
        int typeId = resultSet.getInt("type_id");
        int year = resultSet.getInt("reg_year");
        int month = resultSet.getInt("reg_month");
        int userId = resultSet.getInt("person_id");
        User user = userRepository.get(userId).get();
        TypeOfValue type = typeOfValueRepository.get(typeId).get();
        double measurement_value = resultSet.getDouble("measurement_value");
        return new Measurement(measurementId, type, year, month, user, measurement_value);
    }
}