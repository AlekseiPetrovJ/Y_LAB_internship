package ru.petrov.consoleApp;

import ru.petrov.model.Role;

import static ru.petrov.Initialization.getCurrentUser;
import static ru.petrov.Initialization.messenger;

public class WelcomePage {
    private static final int REGISTRATION = 1;
    private static final int LOGIN = 2;

    private static final int LOGOUT = 3;

    private static final int VIEW_ADD_TYPE_OF_VALUE = 4;
    private static final int VIEW_ACTUAL_MEASUREMENTS = 5;


    private static final int EXIT = 99;

    public static void showMenu() {
        int command = 0;
        messenger.sendString(getHeadMenu());

        while (command != EXIT) {
            messenger.sendString(getBodyMenu());
            command = messenger.getInt();
            if (!validationCommand(command)) {
                messenger.sendString("Не валидная команда.\n");
                continue;
            }

            switch (command) {
                case REGISTRATION -> RegistrationUserPage.registration();
                case LOGIN -> AuthorizationPage.login();
                case LOGOUT -> AuthorizationPage.logout();
                case VIEW_ADD_TYPE_OF_VALUE -> ViewTypesOfValuePage.view();
                case VIEW_ACTUAL_MEASUREMENTS -> ActualMeasurementsPage.getActualMeasurements()
                        .forEach(measurement -> messenger.sendString(measurement.toString()));
                case EXIT -> messenger.sendString("Работа программы завершена.");
            }
        }
    }

    public static String getHeadMenu() {
        return "Добро пожаловать в систему учета показаний!\n";
    }

    public static String getBodyMenu() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nМеню:\n");
        if (getCurrentUser().isEmpty()) {
            sb.append("Авторизуйтесь для отображения полного меню.\n");
            sb.append(REGISTRATION).append(" - Регистрация\n");
            sb.append(LOGIN).append(" - Авторизация\n");
        } else {
            sb.append(LOGOUT).append(" - Выход из профиля\n");
            sb.append(VIEW_ADD_TYPE_OF_VALUE).append(" - Просмотр, добавление типов показаний\n");
            sb.append(VIEW_ACTUAL_MEASUREMENTS).append(" - Получение актуальных показаний счетчиков\n");

        }
        if (getCurrentUser().isPresent() && getCurrentUser().get().getRole().equals(Role.ADMIN)) {
            //TODO Дальше только админ
        }
        sb.append(EXIT).append(" - Выход из приложения\n");
        sb.append("Введите номер команды:");
        return sb.toString();
    }

    private static boolean validationCommand(int command) {
        if (getCurrentUser().isEmpty()
                && (command == REGISTRATION || command == LOGIN || command == EXIT)) {
            return true;
        } else if (getCurrentUser().isPresent()) {
            return (getCurrentUser().get().getRole().equals(Role.USER) || (getCurrentUser().get().getRole().equals(Role.ADMIN)))
                    && (command == REGISTRATION || command == LOGOUT || command == EXIT
                    || command == VIEW_ADD_TYPE_OF_VALUE || command == VIEW_ACTUAL_MEASUREMENTS);
        }
        return false;
    }
}


