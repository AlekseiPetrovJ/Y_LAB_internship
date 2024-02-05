package ru.petrov.consoleApp;

import ru.petrov.model.Role;
import ru.petrov.model.User;

import static ru.petrov.Initialization.consoleHelper;
import static ru.petrov.Initialization.userRepository;

public class RegistrationMeasurementPage {
    public static void registration() {
        System.out.print("Страница регистрации показаний.\n");
        System.out.print("Выберете тип показаний:");
        //TODO Дописать
        String userName;
        while (true) {
            System.out.print("Введите имя:");
            userName = consoleHelper.getString();
            if (userName.equals("q")){
                break;
            }
            if (userRepository.get(userName).isPresent()) {
                System.out.printf(userName +
                        " - скорее всего имя не уникально. Попробуйте придумать другое имя. \n" +
                        "Введите \"q\" для возврата в главное меню.\n");
            } else {
                System.out.print("Введите пароль:");
                String userPass = consoleHelper.getPassword();
                userRepository.save(new User(userName, userPass, Role.USER));
                System.out.printf("Пользователь " + userName + " успешно зарегистрирован. \n");
                break;
            }
        }
    }
}
