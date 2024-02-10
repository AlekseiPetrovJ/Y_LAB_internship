package ru.petrov.consoleApp;

import ru.petrov.model.TypeOfValue;

import static ru.petrov.Initialization.consoleHelper;
import static ru.petrov.Initialization.typeOfValueRepository;

public class RegistrationTypeOfValuePage {
    public static void registration() {
        System.out.print("Страница регистрации типа показаний.\n");
        System.out.print("Введите название типа:");
        String nameType = consoleHelper.getString();
        System.out.print("Введите ед. измерения типа:");
        String nameUnitType = consoleHelper.getString();

        if (typeOfValueRepository.save(new TypeOfValue(nameType, nameUnitType)).isPresent()) {
            System.out.printf("Тип показаний " + "\"" + nameType + "\"" + " создан.\n");
        } else {
            System.out.printf(nameType +
                    " - скорее всего назване не уникально. Попробуйте придумать другое название. \n");
        }
    }
}



