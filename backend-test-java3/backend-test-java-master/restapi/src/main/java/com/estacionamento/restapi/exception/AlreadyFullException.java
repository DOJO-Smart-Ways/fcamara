package com.estacionamento.restapi.exception;

public class AlreadyFullException extends RuntimeException{
    public AlreadyFullException(String message) {
        super(message);
    }
}
