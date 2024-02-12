package com.estacionamento.restapi.handler;

import com.estacionamento.restapi.exception.*;
import com.estacionamento.restapi.exception.message.AlreadyExistsExceptionMessage;
import com.estacionamento.restapi.exception.message.AlreadyFullExceptionMessage;
import com.estacionamento.restapi.exception.message.NotFoundExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundExceptionMessage> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new NotFoundExceptionMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), e.getMessage()));

    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<AlreadyExistsExceptionMessage> handleAlreadyExists(AlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AlreadyExistsExceptionMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(AlreadyFullException.class)
    public ResponseEntity<AlreadyFullExceptionMessage> handleAlreadyFull(AlreadyFullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AlreadyFullExceptionMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

}
