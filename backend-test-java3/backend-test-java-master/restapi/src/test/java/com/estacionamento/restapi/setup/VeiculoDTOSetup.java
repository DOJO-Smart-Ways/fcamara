package com.estacionamento.restapi.setup;

import com.estacionamento.restapi.dtos.VeiculoDTO;
import com.estacionamento.restapi.model.TipoVeiculo;

public class VeiculoDTOSetup {
    public static VeiculoDTO createValidVeiculo() {
        return defaultBuilder().build();
    }



    public static VeiculoDTO.VeiculoDTOBuilder defaultBuilder() {
        return VeiculoDTO.builder()
                .modelo("Modelo Teste")
                .marca("Marca de Teste")
                .placa("HTM5196")
                .cor("Vermelho")
                .tipo(TipoVeiculo.valueOf("CARRO"));
    }
}
