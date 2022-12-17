package com.example.website;

import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InfoExistedException.class)
    public ResponseEntity<Object> handleInfoExistedException(
            InfoExistedException ex, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","Information Existed");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InfoNotFoundException.class)
    public ResponseEntity<Object> handleInfoNotFoundException(
            InfoNotFoundException ex, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("message","Information Not Found");

        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(
            InvalidInputException ex, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("message","Invalid Input");

        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status",status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x->x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("error",errors);

        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);

    }

}
