package com.tamanna.interviewcalendar.config;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return buildResponse(BAD_REQUEST, errors);
    }

    @ExceptionHandler
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) throws IOException {
        return buildResponse(BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> constraintViolationException(EntityExistsException ex) throws IOException {
        return buildResponse(BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex) throws IOException {
        return buildResponse(BAD_REQUEST, List.of(ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);
        return ResponseEntity.status(status.value()).body(body);
    }


}
