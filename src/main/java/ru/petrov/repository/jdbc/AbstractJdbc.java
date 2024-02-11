package ru.petrov.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AbstractJdbc {
    public static Connection getConnection()  {
        ResourceBundle resource = ResourceBundle.getBundle("db/database");
        String url = resource.getString("url");
        String db_user = resource.getString("db_user");
        String pass = resource.getString("password");
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, db_user, pass);
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("not geted connection " + e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, db_user, pass);
        } catch (SQLException e) {
            //TODO перенести в лог
            System.out.println("not geted connection" + e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }
}
