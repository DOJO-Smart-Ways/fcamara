package com.estacionamento.restapi.setup;

import com.estacionamento.restapi.model.TipoVeiculo;
import com.estacionamento.restapi.model.Veiculo;

public class VeiculoSetup {
    public static Veiculo createValidVeiculo() {
        return defaultBuilder().build();
    }


    public static Veiculo.VeiculoBuilder defaultBuilder() {
        return Veiculo.builder().id(1)
                .modelo("Modelo Teste")
                .marca("Marca de Teste")
                .placa("HTM5196")
                .cor("Vermelho")
                .tipo(TipoVeiculo.valueOf("CARRO"));
    }
}
