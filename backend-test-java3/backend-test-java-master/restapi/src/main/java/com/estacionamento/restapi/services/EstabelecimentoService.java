package com.estacionamento.restapi.services;

import java.util.List;
import java.util.Optional;

import com.estacionamento.restapi.dtos.EstabelecimentoDTO;
import com.estacionamento.restapi.exception.AlreadyExistException;
import com.estacionamento.restapi.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamento.restapi.model.Estabelecimento;
import com.estacionamento.restapi.repository.EstabelecimentoRepository;


@Service
public class EstabelecimentoService {

    //Injecao de Dependencia
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    //Metodos Service

    public List<Estabelecimento> findAll() {
        return estabelecimentoRepository.findAll();
    }

    public Estabelecimento findById(Integer id) throws NotFoundException {
        Estabelecimento foundEstabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("O Estabelecimento com id %s não foi encontrado",id)));
        return foundEstabelecimento;
    }

    public Estabelecimento findByCnpj(String cnpj) throws NotFoundException {
        Estabelecimento foundEstabelecimento = estabelecimentoRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException(String.format("Estabelecimento com CNPJ %s não foi encontrado",cnpj)));
        return foundEstabelecimento;
    }

    public Estabelecimento create(EstabelecimentoDTO estabelecimentoDTO) {
        verifyIfAlreadyRegistered(estabelecimentoDTO.getCnpj());
        var estabelecimentoModel = new Estabelecimento();
        BeanUtils.copyProperties(estabelecimentoDTO, estabelecimentoModel);
        return estabelecimentoRepository.save(estabelecimentoModel);
    }

    public String delete(Integer id) throws NotFoundException {
       verifyExists(id);
       estabelecimentoRepository.deleteById(id);
       return "Estabelecimento deletado com sucesso";
    }

    public Estabelecimento update(Estabelecimento estabelecimentoModel, Integer id) throws NotFoundException {
        verifyExists(id);
        return estabelecimentoRepository.save(estabelecimentoModel);
    }

       private Estabelecimento verifyExists(Integer id) throws NotFoundException {
        return estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Estabelecimento com id %s não foi encontrado",id)));
       }

       private void verifyIfAlreadyRegistered(String cnpj) throws AlreadyExistException {
           Optional<Estabelecimento> existedEstabelecimento = estabelecimentoRepository.findByCnpj(cnpj);
           if (existedEstabelecimento.isPresent()) {
               throw new AlreadyExistException(String.format("Estabelecimento com o CNPJ %s já foi registrado",cnpj));
           }
       }
}
