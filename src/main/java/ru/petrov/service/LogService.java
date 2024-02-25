package ru.petrov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.petrov.model.Log;
import ru.petrov.model.User;
import ru.petrov.repository.LogRepository;
import ru.petrov.repository.UserRepository;

import java.util.Optional;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Autowired
    public LogService(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    public Optional<Log> save(Log log) {
        if (log.getUser() == null) {
            User user = userRepository.get("anonymousUser").get();
            return logRepository.save(new Log(log.getRegistered(), log.getLevel(), user, log.getLogValue(), log.getDuration()));
        }
        return logRepository.save(log);
    }
}