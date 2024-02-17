package ru.petrov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.petrov.model.TypeOfValue;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.util.EntityNotFoundException;

import java.util.Optional;

@Service
public class TypeOfValueService {
    private final TypeOfValueRepository typeOfValueRepository;

    @Autowired
    public TypeOfValueService(TypeOfValueRepository typeOfValueRepository) {
        this.typeOfValueRepository = typeOfValueRepository;
    }

    public TypeOfValue get(int id) {
        Optional<TypeOfValue> foundType = typeOfValueRepository.get(id);
        return foundType.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<TypeOfValue> get(String name) {
        return typeOfValueRepository.get(name);
    }

    public Optional<TypeOfValue> save(TypeOfValue type) {
        return typeOfValueRepository.save(type);
    }
}