package ru.petrov.consoleApp;

import ru.petrov.model.TypeOfValue;

import static ru.petrov.Initialization.messenger;
import static ru.petrov.Initialization.typeOfValueRepository;

public class RegistrationTypeOfValuePage {
    public static void registration() {
        messenger.sendString("Страница регистрации типа показаний.\n");
        messenger.sendString("Введите название типа:");
        String nameType = messenger.getString();
        messenger.sendString("Введите ед. измерения типа:");
        String nameUnitType = messenger.getString();

        if (typeOfValueRepository.save(new TypeOfValue(nameType, nameUnitType)).isPresent()) {
            messenger.sendString("Тип показаний " + "\"" + nameType + "\"" + " создан.\n");
        } else {
            messenger.sendString(nameType +
                    " - скорее всего назване не уникально. Попробуйте придумать другое название. \n");
        }
    }
}



