package ru.petrov.consoleApp;

import ru.petrov.model.Role;

import static ru.petrov.Initialization.*;

public class ViewTypesOfValuePage {
    public static void view() {
        messenger.sendString("Страница просмотра типа показаний.\n");
        messenger.sendString("Существующие типы:\n");
        while (true) {
            typeOfValueRepository.getAll().
                    forEach(t -> messenger.sendString(t.toString()));
            messenger.sendString("Меню:\n");
            if (getCurrentUser().isPresent() && getCurrentUser().get().getRole().equals(Role.ADMIN)) {
                messenger.sendString("1 - добавить тип показания\n");
            }
            messenger.sendString("2 - для возврата в главное меню.\n");
            messenger.sendString("Введите номер команды:");
            int command = messenger.getInt();
            if (command == 1 && getCurrentUser().isPresent() && getCurrentUser().get().getRole().equals(Role.ADMIN)) {
                RegistrationTypeOfValuePage.registration();
            } else if (command == 2) {
                break;
            } else {
                messenger.sendString("Не валидная команда.\n");
            }
        }


    }
}


