package ru.petrov.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.petrov.dto.TypeOfValueDto;
import ru.petrov.model.TypeOfValue;
import ru.petrov.service.TypeOfValueService;
import ru.petrov.util.CheckBindingResult;
import ru.petrov.util.validator.TypeOfValueValidator;

import java.util.Optional;

@RestController
@RequestMapping(path = "/type")
@SecurityRequirement(name = "basicAuth")
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

        new CheckBindingResult().check(bindingResult);

        Optional<TypeOfValue> save = typeService.save(type);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(save.get().getId()).toUri()).build();
    }
}