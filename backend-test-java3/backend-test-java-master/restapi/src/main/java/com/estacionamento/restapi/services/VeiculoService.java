package com.estacionamento.restapi.services;

import com.estacionamento.restapi.dtos.VeiculoDTO;
import com.estacionamento.restapi.exception.AlreadyExistException;
import com.estacionamento.restapi.exception.NotFoundException;
import com.estacionamento.restapi.model.Estabelecimento;
import com.estacionamento.restapi.model.Veiculo;
import com.estacionamento.restapi.repository.VeiculoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRepository veiculoRepository;

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo findById(Integer id)throws NotFoundException {
        Veiculo foundVeiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("O veiculo com %s não foi encontrado",id)));
        return foundVeiculo;
    }

    public Object findByPlaca(String placa) {
        Veiculo foundVeiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new NotFoundException(String.format("Veiculo com a placa %s não foi encontrado",placa)));
        return foundVeiculo;
    }

    public Veiculo create(VeiculoDTO veiculoDTO) {
        verifyIfAlreadyRegistered(veiculoDTO.getPlaca());
        var veiculoModel = new Veiculo();
        BeanUtils.copyProperties(veiculoDTO, veiculoModel);
        return veiculoRepository.save(veiculoModel);
    }
    public String delete(Integer id)throws NotFoundException {
        verifyExists(id);
        veiculoRepository.deleteById(id);
        return "Veiculo deletado com sucesso";
    }

    public Veiculo update(Veiculo veiculoModel, Integer id) throws NotFoundException {
        verifyExists(id);
        return veiculoRepository.save(veiculoModel);
    }

    private Veiculo verifyExists(Integer id) throws NotFoundException {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Veiculo com id %s não foi encontrado",id)));
    }

    private void verifyIfAlreadyRegistered(String placa) throws AlreadyExistException {
        Optional<Veiculo> existedVeiculo = veiculoRepository.findByPlaca(placa);
        if (existedVeiculo.isPresent()) {
            throw new AlreadyExistException(String.format("Veiculo com a placa %s já foi registrado",placa));
        }
    }



}
