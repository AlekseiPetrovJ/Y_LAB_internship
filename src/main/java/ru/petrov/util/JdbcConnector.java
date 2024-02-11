package ru.petrov.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    public static Connection getConnection()  {
        String url = PropertyUtil.getProperty("db/database", "url" );
        String db_user = PropertyUtil.getProperty("db/database", "db_user" );
        String pass = PropertyUtil.getProperty("db/database", "password" );
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
