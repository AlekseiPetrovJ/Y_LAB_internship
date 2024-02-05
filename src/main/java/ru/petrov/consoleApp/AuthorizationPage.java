package ru.petrov.consoleApp;

import ru.petrov.Initialization;
import ru.petrov.model.User;

import java.util.Optional;

import static ru.petrov.Initialization.*;

public class AuthorizationPage {
    public static void login() {
        System.out.print("Страница авторизации.\n");
        System.out.print("Введите имя:");
        String userName = consoleHelper.getString();
        System.out.print("Введите пароль:");
        String userPass = consoleHelper.getPassword();

        Optional<User> authorization = Initialization.authorizationService.authorization(userName, userPass);

        if (authorization.isEmpty()) {
            System.out.print("\nТакое имя и пароль не найдены в базе.\n\n");
        } else {
            System.out.printf("Пользователь " + authorization.get().getName() + " - авторизован.\n");
            Initialization.setCurrentUser(authorization);
        }
    }

    public static void logout() {
        if (getCurrentUser().isPresent()) {
            System.out.printf("Пользователь " + getCurrentUser().get().getName() +
                    " вышел из профиля\n");
            setCurrentUser(Optional.empty());
        }
    }
}
