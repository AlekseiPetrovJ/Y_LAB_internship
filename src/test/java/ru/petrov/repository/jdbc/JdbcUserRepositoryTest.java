package ru.petrov.repository.jdbc;

import ru.petrov.repository.AbstractUserRepositoryTest;

class JdbcUserRepositoryTest extends AbstractUserRepositoryTest {
    {
        setUserRepository(new JdbcUserRepository());
    }
}