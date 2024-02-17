package ru.petrov.util;

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
        if (type.getUnitOfMeasurement()==null) {
            errors.rejectValue("unitOfMeasurement", "", "- ед. измерения должна быть заполнена");
        } else if (type.getUnitOfMeasurement().isEmpty()){
            errors.rejectValue("unitOfMeasurement", "", "- ед. измерения не должна быть пустой");
        }

        if (type.getName() == null) {
            errors.rejectValue("name", "", "Имя должно быть заполнено");
        } else if (type.getName().isEmpty()) {
            errors.rejectValue("name", "", "Имя не должно быть пустым");
        } else if (typeService.get(type.getName()).isPresent()) {
            errors.rejectValue("name", "", "Измерение с таким именем уже существует");
        }
    }
}