package ru.petrov.consoleApp;

import ru.petrov.model.Role;
import ru.petrov.model.User;

import static ru.petrov.Initialization.messenger;
import static ru.petrov.Initialization.userRepository;

public class RegistrationUserPage {
    public static void registration() {
        messenger.sendString("Страница регистрации пользователя.\n");
        String userName;
        while (true) {
            messenger.sendString("Введите имя:");
            userName = messenger.getString();
            if (userName.equals("q")){
                break;
            }
            if (userRepository.get(userName).isPresent()) {
                messenger.sendString(userName +
                        " - скорее всего имя не уникально. Попробуйте придумать другое имя. \n" +
                        "Введите \"q\" для возврата в главное меню.\n");
            } else {
                messenger.sendString("Введите пароль:");
                String userPass = messenger.getPassword();
                userRepository.save(new User(userName, userPass, Role.USER));
                messenger.sendString("Пользователь " + userName + " успешно зарегистрирован. \n");
                break;
            }
        }
    }

}
