package ru.petrov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.petrov.model.Role;
import ru.petrov.model.User;
import ru.petrov.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
    @InjectMocks
    private AuthorizationService authorizationService;
    @Mock
    private UserRepository userRepository;

    @Test
    void authorizationPass() {

        User expectedUser = new User("Ivan", "333", Role.USER);
        Mockito.when(userRepository.get("Ivan"))
                .thenReturn(Optional.of(expectedUser));
        Optional<User> actual = authorizationService.authorization("Ivan", "333");
        assertEquals(Optional.of(expectedUser), actual);
    }

    @Test
    void authorizationDeny() {

        User expectedUser = new User("Ivan", "333", Role.USER);
        Mockito.when(userRepository.get("Ivan"))
                .thenReturn(Optional.of(expectedUser));
        Optional<User> actual = authorizationService.authorization("Ivan", "222");
        assertEquals(Optional.empty(), actual);
    }
}