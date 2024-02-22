package ru.petrov.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.petrov.annotations.Loggable;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.mapper.UserMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> save(User user) {
        String query;
        int result;
        if (user.isNew()) {
            query = "INSERT INTO person (name, password, role_id)\n" +
                    "VALUES (?, ?, ?);";
            result = jdbcTemplate.update(query,
                    user.getName(),
                    user.getPassword(),
                    user.getRole().getRoleId());
        } else {
            query = "UPDATE person SET name = ?, password=?, role_id = ?, registered = ? WHERE person_id=?;";
            result = jdbcTemplate.update(query,
                    user.getName(),
                    user.getPassword(),
                    user.getRole().getRoleId(),
                    Timestamp.valueOf(user.getRegistered()),
                    user.getId());
        }
        if (result>0){
            return get(user.getName());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from person where person_id=?";
        return jdbcTemplate.update(query, new Object[]{id})>0;
    }

    @Override
    @Loggable
    public Optional<User> get(Integer id) {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id " +
                "where person_id=?";
        return jdbcTemplate.query(selectSql, new Object[]{id}, new UserMapper())
                .stream()
                .findAny();
    }

    @Override
    @Loggable
    public Optional<User> get(String name) {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id " +
                "where name=?";
        return jdbcTemplate.query(selectSql, new Object[]{name}, new UserMapper())
                .stream()
                .findAny();
    }

    @Override
    public List<User> getAll() {
        String selectSql = "select * from person LEFT JOIN person_role ON person_role.role_id=person.role_id ";
        return jdbcTemplate.query(selectSql, new UserMapper());
    }
}
