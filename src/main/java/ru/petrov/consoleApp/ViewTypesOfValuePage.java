package ru.petrov.consoleApp;

import ru.petrov.model.Role;

import static ru.petrov.Initialization.consoleHelper;
import static ru.petrov.Initialization.getCurrentUser;
import static ru.petrov.Initialization.typeOfValueRepository;

public class ViewTypesOfValuePage {
    public static void view() {
        System.out.print("Страница просмотра типа показаний.\n");
        System.out.print("Существующие типы:\n");
        while (true) {
            typeOfValueRepository.getAll().
                    forEach(t -> System.out.printf(t.toString()));
            System.out.print("Меню:\n");
            if (getCurrentUser().isPresent() && getCurrentUser().get().getRole().equals(Role.ADMIN)) {
                System.out.print("1 - добавить тип показания\n");
            }
            System.out.print("2 - для возврата в главное меню.\n");
            System.out.print("Введите номер команды:");
            int command = consoleHelper.getInt();
            if (command == 1 && getCurrentUser().isPresent() && getCurrentUser().get().getRole().equals(Role.ADMIN)) {
                RegistrationTypeOfValuePage.registration();
            } else if (command == 2) {
                break;
            } else {
                System.out.print("Не валидная команда.\n");
            }
        }


    }
}


