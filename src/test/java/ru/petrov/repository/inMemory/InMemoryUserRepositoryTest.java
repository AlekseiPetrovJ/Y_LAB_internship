package ru.petrov.repository.inMemory;

import ru.petrov.repository.AbstractUserRepositoryTest;

class InMemoryUserRepositoryTest extends AbstractUserRepositoryTest {
    {
        setUserRepository(new InMemoryUserRepository());
    }
}