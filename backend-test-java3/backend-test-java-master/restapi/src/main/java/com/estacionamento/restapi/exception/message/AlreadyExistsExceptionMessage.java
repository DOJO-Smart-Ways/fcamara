package com.estacionamento.restapi.exception.message;

import com.estacionamento.restapi.exception.message.ExceptionMessage;

import java.time.LocalDateTime;

public class AlreadyExistsExceptionMessage extends ExceptionMessage {
    public AlreadyExistsExceptionMessage(LocalDateTime timestamp, int status, String message) {
        super(timestamp, status, message);
    }
}
