package ru.petrov.util.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.petrov.model.TypeOfValue;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeOfValueRowMapper implements RowMapper<TypeOfValue> {
    @Override
    public TypeOfValue mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("type_id");
        String name = resultSet.getString("name");
        String unitOfMeasurement = resultSet.getString("unit_of_measurement");
        return new TypeOfValue(id, name, unitOfMeasurement);
    }
}