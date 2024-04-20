package ru.petrov.util.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrov.model.TypeOfValue;
import ru.petrov.service.TypeOfValueService;

@Component
public class TypeOfValueValidator implements Validator {
    private final TypeOfValueService typeService;

    public TypeOfValueValidator(TypeOfValueService typeService) {
        this.typeService = typeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TypeOfValue.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TypeOfValue type = (TypeOfValue) target;
        if (typeService.get(type.getName()).isPresent()) {
            errors.rejectValue("name", "", "Измерение с таким именем уже существует");
        }
    }
}