package com.estacionamento.restapi.setup;

import com.estacionamento.restapi.dtos.EstabelecimentoDTO;
import com.estacionamento.restapi.model.Estabelecimento;

public class EstabelecimentoDTOSetup {
    public static EstabelecimentoDTO createValidEstabelecimento() {
        return defaultBuilder().build();
    }



    public static EstabelecimentoDTO.EstabelecimentoDTOBuilder defaultBuilder() {
        return EstabelecimentoDTO.builder()
        .nome("Estabelecimento Teste")
        .cnpj("38932768000176")
        .endereco("Rua de Teste, Bairro de Teste")
        .telefone("12927837782")
        .vagasCarros(40)
        .vagasMotos(20);
    }
}
