package ru.petrov.util.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrov.model.User;
import ru.petrov.service.UserService;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getPassword()==null) {
            errors.rejectValue("password", "", "- должен быть заполнен");
        } else if (user.getPassword().isEmpty()){
            errors.rejectValue("password", "", "- не должен быть пустым");
        }

        if (user.getName() == null) {
            errors.rejectValue("name", "", "Имя должно быть заполнено");
        } else if (user.getName().length() < 3 || user.getName().length() > 60) {
            errors.rejectValue("name", "", "Имя должно быть больше 3 знаков и меньше 60");
        } else if (userService.get(user.getName()).isPresent()) {
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");
        }
    }
}

