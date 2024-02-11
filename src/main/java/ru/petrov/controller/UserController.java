package ru.petrov.controller;

import org.modelmapper.ModelMapper;
import ru.petrov.dto.UserDto;
import ru.petrov.dto.UserInDto;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

public class UserController {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public UserController(ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public UserDto get(int id) {
        return mapper.map(userRepository.get(id), UserDto.class);
    }

    public UserDto get(String name) {
        return mapper.map(userRepository.get(name), UserDto.class);
    }

    public UserDto save(UserInDto userInDto){
        User user = new User(userInDto.getName(), userInDto.getPassword(), Role.USER);
        return mapper.map(userRepository.save(user),UserDto.class);
    }
}
