package ru.petrov;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.petrov.repository.jdbc.AbstractJdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class InitializationDb extends AbstractJdbc {
    public static void migration(){
        try (Connection connection = getConnection()) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            //TODO перенести в лог
            System.out.println("Migration is completed successfully");
        } catch (SQLException | LiquibaseException e) {
            //TODO перенести в лог
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }

    public static void migrationForTest(){
        try (Connection connection = getConnection()) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase.Liquibase liquibase =
                    new liquibase.Liquibase("db/changelog/changelog-for-test.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            //TODO перенести в лог
            System.out.println("Migration is completed successfully");
        } catch (SQLException | LiquibaseException e) {
            //TODO перенести в лог
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }

}
