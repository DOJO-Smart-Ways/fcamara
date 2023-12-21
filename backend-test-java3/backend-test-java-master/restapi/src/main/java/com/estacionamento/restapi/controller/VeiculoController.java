package com.estacionamento.restapi.controller;

import com.estacionamento.restapi.dtos.EstabelecimentoDTO;
import com.estacionamento.restapi.dtos.VeiculoDTO;
import com.estacionamento.restapi.exception.NotFoundException;
import com.estacionamento.restapi.model.Estabelecimento;
import com.estacionamento.restapi.model.Veiculo;
import com.estacionamento.restapi.services.VeiculoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/veiculo")
@CrossOrigin(origins = "*")
@Api(value = "API Rest Estacionamento")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    //Lista todos os veiculos
    @GetMapping
    @ApiOperation(value = "Essa rota é responsavel por listar todos os Veículos")
    public ResponseEntity<List<Veiculo>> findAllVeiculos() {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.findAll());
    }

    //Busca um Veiculo pelo id
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Essa rota é responsavel por buscar um veículo pelo id")
    public ResponseEntity<Object> findVeiculoById(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.findById(id));
    }

    @GetMapping(path = "/placa/{placa}")
    @ApiOperation(value = "Essa rota é responsavel por buscar um veículo pela placa")
    public ResponseEntity<Object> findVeiculoByPlaca(@PathVariable(value = "placa")String placa)throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.findByPlaca(placa));
    }

    @PostMapping
    @ApiOperation(value = "Essa rota é responsavel por criar um veículo")
    public ResponseEntity<Object> createVeiculo(@Valid @RequestBody VeiculoDTO veiculoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.create(veiculoDTO));
    }
@DeleteMapping(path = "/{id}")
@ApiOperation(value = "Essa rota é responsavel por deletar um veículo")
    public ResponseEntity<String> deleteVeiculo(@PathVariable(value = "id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.delete(id));
    }
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Essa rota é responsavel por atualizar um veículo")
    public ResponseEntity<Object> updateVeiculo(@RequestBody @Valid VeiculoDTO veiculoDTO,
                                                        @PathVariable(value = "id") Integer id) throws NotFoundException {
        //Recebe o veiculo existente
        Veiculo veiculoExists = veiculoService.findById(id);

        //Faz o veiculo existente copiar os dados fornecidos
        BeanUtils.copyProperties(veiculoDTO, veiculoExists);
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.update(veiculoExists, id));
    }
}
