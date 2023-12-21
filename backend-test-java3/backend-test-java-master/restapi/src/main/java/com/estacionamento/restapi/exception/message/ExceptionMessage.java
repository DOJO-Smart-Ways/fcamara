package com.estacionamento.restapi.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
public class ExceptionMessage {
    private LocalDateTime timestamp;
    private int status;
    private String message;

}
