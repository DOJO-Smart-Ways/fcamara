package com.estacionamento.restapi.dtos;

import javax.validation.constraints.*;


import lombok.*;

import org.hibernate.validator.constraints.br.CNPJ;

@Builder
@Data
public class EstabelecimentoDTO {
   
    @NotBlank(message = "O Campo nome não pode ser deixado em branco")
    private String nome;
    
    @NotBlank(message = "O campo CNPJ não pode ser deixado em branco")
    @CNPJ
    private String cnpj;

    @NotBlank(message = "O campo endereço não pode ser deixado em branco")
    private String endereco;

    @NotBlank(message = "O campo telefone não pode ser deixado em branco")
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]*")
    private String telefone;
    
    @NotNull
    private int vagasCarros;
    
    @NotNull
    private int vagasMotos;
}
