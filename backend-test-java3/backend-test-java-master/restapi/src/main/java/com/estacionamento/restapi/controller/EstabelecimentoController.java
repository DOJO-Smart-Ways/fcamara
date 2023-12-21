package com.estacionamento.restapi.controller;


import java.util.List;

import javax.validation.Valid;

import com.estacionamento.restapi.exception.AlreadyExistException;
import com.estacionamento.restapi.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estacionamento.restapi.dtos.EstabelecimentoDTO;
import com.estacionamento.restapi.model.Estabelecimento;
import com.estacionamento.restapi.services.EstabelecimentoService;


@RestController
@RequestMapping(path = "/estabelecimento")
@CrossOrigin(origins = "*")
@Api(value = "API Rest Estacionamento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    //Lista todos os Estabelecimentos
    @GetMapping
    @ApiOperation(value = "Essa rota é responsavel por Listar todos os Estabelecimentos")
    public ResponseEntity<List<Estabelecimento>> findAllEstabelecimento() {
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoService.findAll());
    }

    //Busca um Estabelecimento pelo id
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Essa rota é responsavel por buscar um Estabelecimento pelo id")
    public ResponseEntity<Object> findEstabelecimentoById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoService.findById(id));
    }

    //Busca um Estabeelcimento pelo CNPJ
    @GetMapping(path = "/cnpj/{cnpj}")
    @ApiOperation(value = "Essa rota é responsavel por buscar um Estabelecimento por CNPJ ")
    public ResponseEntity<Object> findEstabelecimentoByCNPJ(@PathVariable(value = "cnpj")String cnpj)throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoService.findByCnpj(cnpj));
    }

    // Cria um novo Estabelecimento
    @PostMapping
    @ApiOperation(value = "Essa rota é responsavel por criar um Estabelecimento")
    public ResponseEntity<Object> createEstabelecimento(@RequestBody @Valid EstabelecimentoDTO estabelecimentoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoService.create(estabelecimentoDTO));
    }

    //Deleta um Estabelecimento
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Essa rota é responsavel por deletar um Estabelecimento")
    public ResponseEntity<String> deleteEstabelecimento(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoService.delete(id));
    }

    // Atualiza um Estabelecimento
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Essa rota é responsavel por atualizar um Estabelecimento")
    public ResponseEntity<Object> updateEstabelecimento(@RequestBody @Valid EstabelecimentoDTO estabelecimentoDTO,
                                                        @PathVariable(value = "id") Integer id) throws NotFoundException {
        //Recebe o estabelecimento
       Estabelecimento estabelecimentoExists = estabelecimentoService.findById(id);

       //Recebe os dados do estabelecimento
        BeanUtils.copyProperties(estabelecimentoDTO, estabelecimentoExists);
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoService.update(estabelecimentoExists, id));
    }
}
    


