package ru.petrov.util.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.petrov.model.Role;
import ru.petrov.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("person_id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        Role role = Enum.valueOf(Role.class, resultSet.getString("role"));
        return new User(id, name, password, role);
    }
}