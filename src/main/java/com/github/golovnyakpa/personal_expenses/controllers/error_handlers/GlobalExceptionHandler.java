package com.github.golovnyakpa.personal_expenses.controllers.error_handlers;

import com.github.golovnyakpa.personal_expenses.dtos.response.ErrorDto;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceForbiddenException;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    // todo return objects instead of string
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> notValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        var errorsString = String.join(", ", errors);
        return new ResponseEntity<>(new ErrorDto(errorsString), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorDto> catchResourceForbiddenException(ResourceForbiddenException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.FORBIDDEN);
    }
}

