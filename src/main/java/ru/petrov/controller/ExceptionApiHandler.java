package ru.petrov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.petrov.util.exception.EntityNotCreatedException;
import ru.petrov.util.exception.EntityNotFoundException;
import ru.petrov.util.exception.ErrorResponse;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    private ResponseEntity<ErrorResponse> handleException(EntityNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Сущность не найдена",
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