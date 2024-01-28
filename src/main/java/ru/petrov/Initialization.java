package ru.petrov;

import ru.petrov.InOut.ConsoleHelper;
import ru.petrov.InOut.Messenger;
import ru.petrov.model.Measurement;
import ru.petrov.service.AuthorizationService;
import ru.petrov.model.Role;
import ru.petrov.model.TypeOfValue;
import ru.petrov.model.User;
import ru.petrov.repository.inMemory.InMemoryMeasurementRepository;
import ru.petrov.repository.inMemory.InMemoryUserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class Initialization {
    public static InMemoryUserRepository userRepository;
    public static InMemoryMeasurementRepository measurementRepository;
    public static AuthorizationService authorizationService;

    private static Optional<User> currentUser;

    public static Messenger messenger;

    public static void init() {
        userRepository = new InMemoryUserRepository();
        measurementRepository = new InMemoryMeasurementRepository(userRepository);
        authorizationService = new AuthorizationService(userRepository);

        messenger = new ConsoleHelper();

        TypeOfValue gas = new TypeOfValue(UUID.randomUUID(), "gas");
        TypeOfValue coldWater = new TypeOfValue(UUID.randomUUID(), "cold water");
        TypeOfValue hotWater = new TypeOfValue(UUID.randomUUID(), "hot water");

        User user = new User("user", "123", Role.USER);
        User admin = new User("admin", "123", Role.ADMIN);
        userRepository.save(user);
        userRepository.save(admin);

        measurementRepository.save(new Measurement(hotWater,  LocalDate.of(2023,11,21), user, 55.0), user.getUuid());
        measurementRepository.save(new Measurement(hotWater,  LocalDate.of(2023,12,20), user, 59.0), user.getUuid());
        measurementRepository.save(new Measurement(coldWater, LocalDate.now(), user, 66),   user.getUuid());
        measurementRepository.save(new Measurement(coldWater, LocalDate.of(2023,12,20), user, 77),   user.getUuid());
        measurementRepository.save(new Measurement(coldWater, LocalDate.of(2023,11,20), user, 88),   user.getUuid());
        measurementRepository.save(new Measurement(coldWater, LocalDate.of(2023,12,20), admin, 77),  admin.getUuid());
        measurementRepository.save(new Measurement(hotWater,  LocalDate.of(2023,12,20), admin, 77),  admin.getUuid());
        measurementRepository.save(new Measurement(gas,       LocalDate.now(), user, 12.5), user.getUuid());

        currentUser = Optional.empty();
    }

    public static Optional<User> getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(Optional<User> currentUser) {
        Initialization.currentUser = currentUser;
    }
}
