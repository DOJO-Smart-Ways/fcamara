package com.estacionamento.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estacionamento.restapi.model.Estabelecimento;

import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer>{
    Optional<Estabelecimento> findByCnpj(String cnpj );
    
}
