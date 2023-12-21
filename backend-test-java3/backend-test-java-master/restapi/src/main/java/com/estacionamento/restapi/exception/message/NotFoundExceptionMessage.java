package com.estacionamento.restapi.exception.message;

import com.estacionamento.restapi.exception.message.ExceptionMessage;

import java.time.LocalDateTime;

public class NotFoundExceptionMessage extends ExceptionMessage {
    public NotFoundExceptionMessage(LocalDateTime timestamp, int status, String message) {
        super(timestamp, status, message);
    }
}
