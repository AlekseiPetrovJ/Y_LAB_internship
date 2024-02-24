package ru.petrov.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.petrov.dto.UserDto;
import ru.petrov.dto.UserInDto;
import ru.petrov.model.User;
import ru.petrov.service.UserService;
import ru.petrov.util.CheckBindingResult;
import ru.petrov.util.validator.UserValidator;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final ModelMapper mapper;
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(ModelMapper mapper, UserService userService, UserValidator userValidator) {
        this.mapper = mapper;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<UserDto> get(@PathVariable("id") int id) {
        return ResponseEntity.ok(mapper.map(userService.get(id), UserDto.class));
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserInDto userInDto,
                                             BindingResult bindingResult) {
        User user = mapper.map(userInDto, User.class);
        user.setName(user.getName().trim());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userValidator.validate(user, bindingResult);

        new CheckBindingResult().check(bindingResult);
        
        Optional<User> save = userService.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(save.get().getId()).toUri()).build();
    }
}
