package ru.petrov.consoleApp;

import ru.petrov.Initialization;
import ru.petrov.model.User;

import java.util.Optional;

import static ru.petrov.Initialization.*;

public class AuthorizationPage {
    public static void login() {
        messenger.sendString("Страница авторизации.\n");
        messenger.sendString("Введите имя:");
        String userName = messenger.getString();
        messenger.sendString("Введите пароль:");
        String userPass = messenger.getPassword();

        Optional<User> authorization = Initialization.authorizationService.Authorization(userName, userPass);

        if (authorization.isEmpty()) {
            messenger.sendString("\nТакое имя и пароль не найдены в базе.\n\n");
        } else {
            messenger.sendString("Пользователь " + authorization.get().getName() + " - авторизован.\n");
            Initialization.setCurrentUser(authorization);
        }
    }

    public static void logout() {
        if (getCurrentUser().isPresent()) {
            messenger.sendString("Пользователь " + getCurrentUser().get().getName() +
                    " вышел из профиля\n");
            setCurrentUser(Optional.empty());
        }
    }
}
