package com.estacionamento.restapi.exception.message;

import java.time.LocalDateTime;

public class AlreadyFullExceptionMessage extends ExceptionMessage{
    public AlreadyFullExceptionMessage(LocalDateTime timestamp, int status, String message) {
        super(timestamp, status, message);
    }
}
