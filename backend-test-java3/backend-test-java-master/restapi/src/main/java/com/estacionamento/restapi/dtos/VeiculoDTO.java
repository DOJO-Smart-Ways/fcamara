package com.estacionamento.restapi.dtos;

import com.estacionamento.restapi.constraint.OnlyLetters;
import com.estacionamento.restapi.model.TipoVeiculo;
import lombok.*;

import javax.validation.constraints.*;

@Builder
@Data
public class VeiculoDTO {

    @NotBlank(message = "O campo marca não pode estar vazio")
    private String marca;

    @NotBlank(message = "O campo modelo não pode estar vazio")
    private String modelo;

    @NotBlank(message = "O campo cor não pode estar vazio")
    @OnlyLetters(message = "A cor deve conter apenas letras")
//    @Pattern(regexp = "^[a-zA-z]*", message = "A cor deve conter apenas letras")
    private String cor;

    @NotBlank(message = "O campo placa não pode estar vazio")
    @Pattern(regexp = "^[a-zA-Z]{3}[0-9][0-9a-zA-Z][0-9]{2}$", message = "A placa inserida está invalída")
    private String placa;

    @NotNull(message = "O tipo do veículo deve ser Carro ou Moto")
    private TipoVeiculo tipo;
}
