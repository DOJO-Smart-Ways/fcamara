package com.estacionamento.restapi.setup;

import com.estacionamento.restapi.model.Estabelecimento;

public class EstabelecimentoSetup {
    public static Estabelecimento createValidEstabelecimento() {
        return defaultBuilder().build();
    }

    public static Estabelecimento updateValidEstabelecimento() {
        return defaultBuilder().id(1)
        .nome("Estabelecimento de Teste Novo")
                .cnpj("38932768000176")

        .build();
    }

    public static Estabelecimento.EstabelecimentoBuilder defaultBuilder() {
        return Estabelecimento.builder().id(1)
        .nome("Estabelecimento Teste")
        .cnpj("38932768000176")
        .endereco("Rua de Teste, Bairro de teste")
        .telefone("12927837782")
        .vagasCarros(40)
        .vagasMotos(20);
    }
}
