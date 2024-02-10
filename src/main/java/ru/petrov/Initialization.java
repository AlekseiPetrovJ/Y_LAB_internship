package ru.petrov;

import ru.petrov.in.ConsoleHelper;
import ru.petrov.model.Measurement;
import ru.petrov.model.TypeOfValue;
import ru.petrov.model.User;
import ru.petrov.repository.MeasurementRepository;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;
import ru.petrov.repository.jdbc.JdbcMeasurementRepository;
import ru.petrov.repository.jdbc.JdbcTypeOfValueRepository;
import ru.petrov.repository.jdbc.JdbcUserRepository;
import ru.petrov.service.AuthorizationService;

import java.util.Optional;

public class Initialization {
    public static UserRepository userRepository;
    public static MeasurementRepository measurementRepository;
    public static AuthorizationService authorizationService;
    public static TypeOfValueRepository typeOfValueRepository;

    private static Optional<User> currentUser;

    public static ConsoleHelper consoleHelper;

    public static void init() {
        /*userRepository = new InMemoryUserRepository();
        measurementRepository = new InMemoryMeasurementRepository(userRepository);
        authorizationService = new AuthorizationService(userRepository);
        typeOfValueRepository = new InMemoryTypeOfValueRepository();*/

        userRepository = new JdbcUserRepository();
        typeOfValueRepository = new JdbcTypeOfValueRepository();
        measurementRepository = new JdbcMeasurementRepository(userRepository, typeOfValueRepository);
        authorizationService = new AuthorizationService(userRepository);

        consoleHelper = new ConsoleHelper();

       /* TypeOfValue gas = new TypeOfValue("gas", "m3");
        TypeOfValue coldWater = new TypeOfValue( "cold water", "m3");
        TypeOfValue hotWater = new TypeOfValue("hot water", "m3");
        typeOfValueRepository.save(gas);
        typeOfValueRepository.save(coldWater);
        typeOfValueRepository.save(hotWater);
*/
//
//        User userT = new User("db_user", "123", Role.USER);
//        User adminT = new User("db_admin", "123", Role.ADMIN);
//
//        userRepository.save(userT);
//        userRepository.save(adminT);

        User user = userRepository.get("user").get();
        User admin = userRepository.get("admin").get();

        TypeOfValue coldWater = typeOfValueRepository.get("cold water").get();
        TypeOfValue hotWater = typeOfValueRepository.get("hot water").get();
        TypeOfValue gas = typeOfValueRepository.get("gas").get();


        measurementRepository.save(new Measurement(hotWater, 2023, 11, user, 55.0), user.getId());
        measurementRepository.save(new Measurement(hotWater, 2023, 12, user, 57.0), user.getId());
        measurementRepository.save(new Measurement(coldWater, 2023, 11, user, 20.0), user.getId());
        measurementRepository.save(new Measurement(coldWater, 2023, 12, user, 51.0), user.getId());
        measurementRepository.save(new Measurement(coldWater, 2024, 1, user, 66.0), user.getId());
        measurementRepository.save(new Measurement(coldWater, 2023, 11, admin, 14.0), admin.getId());
        measurementRepository.save(new Measurement(hotWater, 2023, 12, admin, 34.0), admin.getId());
        measurementRepository.save(new Measurement(gas, 2023, 11, user, 5.0), user.getId());

        currentUser = Optional.empty();
    }

    public static Optional<User> getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(Optional<User> currentUser) {
        Initialization.currentUser = currentUser;
    }
}
