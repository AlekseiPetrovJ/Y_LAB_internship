package ru.petrov.repository;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:15.5");
    protected static DriverManagerDataSource dataSource;

    @BeforeAll
    public static void startContainer() {
        postgresqlContainer.start();
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresqlContainer.getDriverClassName());
        dataSource.setUrl(postgresqlContainer.getJdbcUrl());
        dataSource.setUsername(postgresqlContainer.getUsername());
        dataSource.setPassword(postgresqlContainer.getPassword());
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/changelog-for-test.xml");
        liquibase.setDataSource(dataSource);
        try {
            liquibase.afterPropertiesSet();
            System.out.println("Changelog applied successfully.");
        } catch (LiquibaseException e) {
            System.err.println("Error applying changelog: " + e.getMessage());
        }
    }

    @AfterAll
    public static void stopContainer() {
        postgresqlContainer.stop();
    }


}
