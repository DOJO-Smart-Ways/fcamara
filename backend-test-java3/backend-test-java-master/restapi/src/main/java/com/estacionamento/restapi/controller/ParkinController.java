package com.estacionamento.restapi.controller;

import com.estacionamento.restapi.services.ParkinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/estabelecimento")
@CrossOrigin(origins = "*")
@Api(value = "API Rest Estacionamento")
public class ParkinController {

    @Autowired
    private ParkinService parkinService;

    @PostMapping(path = "/{estabelecimento}/entrada/{veiculo}")
    @ApiOperation(value = "Essa rota é responsavel por realizar a entrada do veículo em um estabelecimento")
    public ResponseEntity<String> entradaDeVeiculo(@PathVariable Integer estabelecimento,
                                                   @PathVariable Integer veiculo) {
        return ResponseEntity.status(HttpStatus.OK).body(parkinService.entrada(estabelecimento, veiculo));
    }

    @PostMapping(path = "/{estabelecimento}/saida/{veiculo}")
    @ApiOperation(value = "Essa rota é responsavel por realizar a saída do veículo em um estabelecimento")
    public ResponseEntity<String> saidaDeVeiculo(@PathVariable Integer estabelecimento,
                                                   @PathVariable Integer veiculo) {
        return ResponseEntity.status(HttpStatus.OK).body(parkinService.saida(estabelecimento, veiculo));
    }
}
