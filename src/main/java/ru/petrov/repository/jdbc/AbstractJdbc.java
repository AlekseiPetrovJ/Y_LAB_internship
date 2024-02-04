package ru.petrov.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AbstractJdbc {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("db/database");
        String url = resource.getString("url");
        String db_user = resource.getString("db_user");
        String pass = resource.getString("password");
        return DriverManager.getConnection(url, db_user, pass);
    }
}
