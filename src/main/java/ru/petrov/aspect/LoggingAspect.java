package ru.petrov.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.petrov.model.Log;
import ru.petrov.model.LogLevel;
import ru.petrov.model.User;
import ru.petrov.security.CustomUserDetails;
import ru.petrov.service.LogService;

@Aspect
@Component
public class LoggingAspect {
    private final LogService logService;

    @Autowired
    public LoggingAspect(LogService logService) {
        this.logService = logService;
    }

    @Around("execution(* *(..)) && @annotation(ru.petrov.annotations.Loggable)")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) {
        Log test_logging = new Log(LogLevel.WARN, getUserFromContext(),
                proceedingJoinPoint.getSignature() + proceedingJoinPoint.getArgs().toString());
        logService.save(test_logging);
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @Around("execution(* *(..)) && @annotation(ru.petrov.annotations.LoggableWithDuration)")
    public Object logWithTime(ProceedingJoinPoint proceedingJoinPoint) {
        long start = System.currentTimeMillis();
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long dur = System.currentTimeMillis() - start;
        Log log = new Log(LogLevel.WARN, getUserFromContext(),
                proceedingJoinPoint.getSignature() + proceedingJoinPoint.getArgs().toString(), dur);
        logService.save(log);
        return result;
    }

    private User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            return principal.getUser();
        }
        return null;
    }
}