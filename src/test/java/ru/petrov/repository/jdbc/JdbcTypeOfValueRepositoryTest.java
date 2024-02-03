package ru.petrov.repository.jdbc;

import ru.petrov.repository.AbstractTypeOfValueRepositoryTest;

class JdbcTypeOfValueRepositoryTest extends AbstractTypeOfValueRepositoryTest {
    {
        setTypeOfValueRepository(new JdbcTypeOfValueRepository());
    }
}