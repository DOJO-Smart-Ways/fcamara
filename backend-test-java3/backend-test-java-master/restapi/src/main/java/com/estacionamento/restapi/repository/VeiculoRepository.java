package com.estacionamento.restapi.repository;

import com.estacionamento.restapi.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estacionamento.restapi.model.Veiculo;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>{
    Optional<Veiculo> findByPlaca(String placa);
}
