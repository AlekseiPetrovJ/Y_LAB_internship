package ru.petrov.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.petrov.dto.TypeOfValueDto;
import ru.petrov.model.TypeOfValue;
import ru.petrov.service.TypeOfValueService;
import ru.petrov.util.EntityNotCreatedException;
import ru.petrov.util.EntityNotFoundException;
import ru.petrov.util.ErrorResponse;
import ru.petrov.util.TypeOfValueValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeOfValueController {
    private final ModelMapper mapper;
    private final TypeOfValueService typeService;
    private final TypeOfValueValidator typeValidator;

    @Autowired
    public TypeOfValueController(ModelMapper mapper, TypeOfValueService typeService, TypeOfValueValidator typeValidator) {
        this.mapper = mapper;
        this.typeService = typeService;
        this.typeValidator = typeValidator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfValueDto> get(@PathVariable("id") int id) {
        return ResponseEntity.ok(mapper.map(typeService.get(id), TypeOfValueDto.class));
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TypeOfValueDto typeDto,
                                             BindingResult bindingResult) {
        TypeOfValue type = mapper.map(typeDto, TypeOfValue.class);
        if (type.getName()!=null){
            type.setName(type.getName().trim());
        }
        typeValidator.validate(type, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsg.toString());
        }
        Optional<TypeOfValue> save = typeService.save(type);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(save.get().getId()).toUri()).build();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    private ResponseEntity<ErrorResponse> handleException(EntityNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Тип измерения не найден",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    private ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        //https://stackoverflow.com/questions/16133923/400-vs-422-response-to-post-of-data
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}