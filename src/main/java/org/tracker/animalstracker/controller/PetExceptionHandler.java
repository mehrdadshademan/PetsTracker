package org.tracker.animalstracker.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tracker.animalstracker.dto.ExceptionDto;
import org.tracker.animalstracker.exception.PetInputNotValidException;
import org.tracker.animalstracker.exception.PetNotFindException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class PetExceptionHandler {
    @ExceptionHandler({PetInputNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionDto handlePetInputNotValidException(PetInputNotValidException ex) {
        return new ExceptionDto(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PetNotFindException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionDto handlePetInputNotValidException(PetNotFindException ex) {
        return new ExceptionDto(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, ExceptionDto> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), new ExceptionDto(error.getDefaultMessage(), HttpStatus.BAD_REQUEST)));
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return errors;
    }
}
