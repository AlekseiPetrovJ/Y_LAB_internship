package ru.petrov.repository.inMemory;

import ru.petrov.repository.AbstractTypeOfValueRepositoryTest;

class InMemoryTypeOfValueRepositoryTest extends AbstractTypeOfValueRepositoryTest {
    {
        setTypeOfValueRepository(new InMemoryTypeOfValueRepository());
    }
}