package com.estacionamento.restapi.services;

import com.estacionamento.restapi.exception.AlreadyFullException;
import com.estacionamento.restapi.exception.NotFoundException;
import com.estacionamento.restapi.model.Estabelecimento;
import com.estacionamento.restapi.model.Veiculo;
import com.estacionamento.restapi.repository.EstabelecimentoRepository;
import com.estacionamento.restapi.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkinService {

   @Autowired
   private EstabelecimentoRepository estabelecimentoRepository;

   @Autowired
   private VeiculoRepository   veiculoRepository;

   public String entrada(Integer estabelecimentoId, Integer veiculoId) {

       Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new NotFoundException(String.format("Estabelecimento com id %s não foi encontrado",estabelecimentoId)));
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow(() -> new NotFoundException(String.format("O veiculo com %s não foi encontrado",veiculoId)));


       switch (veiculo.getTipo()) {
           case CARRO:
               if (estabelecimento.getVagasCarros() == estabelecimento.getNumeroDeCarrosEstacionados())
                   throw new AlreadyFullException(String.format("As vagas do estabelecimento com CNPJ %s já foram preenchidas", estabelecimento.getCnpj()));
               estabelecimento.setNumeroDeCarrosEstacionados(estabelecimento.getNumeroDeCarrosEstacionados() + 1);

           case MOTO:
               if (estabelecimento.getVagasMotos() == estabelecimento.getNumeroDeMotosEstacionados())
                   throw new AlreadyFullException(String.format("As vagas do estabelecimento com CNPJ %s já foram preenchidas", estabelecimento.getCnpj()));
               estabelecimento.setNumeroDeMotosEstacionados(estabelecimento.getNumeroDeMotosEstacionados() + 1);
       }
       estabelecimento.getVeiculos().add(veiculo);
       veiculo.setEstabelecimento(estabelecimento);

       veiculoRepository.save(veiculo);
       estabelecimentoRepository.save(estabelecimento);

       return String.format("O Veiculo foi adicionado ao estabelecimento com CNPJ de %s e nome %s", estabelecimento.getCnpj(),estabelecimento.getNome());
   }

    public String saida(Integer veiculoId, Integer estabelecimentoId) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new NotFoundException(String.format("Estabelecimento com id %s não foi encontrado",estabelecimentoId)));
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow(() -> new NotFoundException(String.format("O veiculo com %s não foi encontrado",veiculoId)));


        switch (veiculo.getTipo()) {
            case CARRO:
                estabelecimento.setNumeroDeCarrosEstacionados(estabelecimento.getNumeroDeCarrosEstacionados() - 1);
            case MOTO:
                estabelecimento.setNumeroDeMotosEstacionados(estabelecimento.getNumeroDeMotosEstacionados() - 1);
        }
        estabelecimento.getVeiculos().remove(veiculo);
        veiculo.setEstabelecimento(null);

        veiculoRepository.save(veiculo);
        estabelecimentoRepository.save(estabelecimento);

        return String.format("O Veiculo saiu do estabelecimento com CNPJ de %s e nome %s", estabelecimento.getCnpj(),estabelecimento.getNome());
    }

//    private Veiculo verifyVeiculoExists(Integer id) throws NotFoundException {
//        return veiculoRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(String.format("Veiculo com id %s não foi encontrado",id)));
//    }
//
//    private Estabelecimento verifyEstabelecimentoExists(Integer id) throws NotFoundException {
//        return estabelecimentoRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(String.format("Estabelecimento com id %s não foi encontrado",id)));
//    }
}
