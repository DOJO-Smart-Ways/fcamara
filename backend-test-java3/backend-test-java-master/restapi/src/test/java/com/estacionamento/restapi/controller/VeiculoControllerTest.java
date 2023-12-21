package com.estacionamento.restapi.controller;

import com.estacionamento.restapi.dtos.VeiculoDTO;
import com.estacionamento.restapi.exception.NotFoundException;
import com.estacionamento.restapi.model.Veiculo;
import com.estacionamento.restapi.services.VeiculoService;
import com.estacionamento.restapi.setup.VeiculoDTOSetup;
import com.estacionamento.restapi.setup.VeiculoSetup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class VeiculoControllerTest {

    @InjectMocks
    private VeiculoController veiculoController;

    @Mock
    private VeiculoService veiculoService;

    private static final Veiculo VALID_VEICULO = VeiculoSetup.createValidVeiculo();

    @BeforeEach
    void setUp() throws NotFoundException {
        when(veiculoService.create(ArgumentMatchers.any(VeiculoDTO.class))).thenReturn(VALID_VEICULO);
        when(veiculoService.findAll()).thenReturn(List.of(VALID_VEICULO));
        when(veiculoService.findById(ArgumentMatchers.anyInt())).thenReturn(VALID_VEICULO);
        when(veiculoService.findByPlaca(ArgumentMatchers.anyString())).thenReturn(VALID_VEICULO);
        when(veiculoService.delete(ArgumentMatchers.anyInt())).thenReturn("Veiculo deletado");
    }

    @Test
    void create_saveVeiculo_WhenSuccessful() {

        ResponseEntity<Object> veiculoEntity = veiculoController.createVeiculo(VeiculoDTOSetup.createValidVeiculo());
        Veiculo body = (Veiculo) veiculoEntity.getBody();

        Assertions.assertThat(veiculoEntity).isNotNull();
        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(veiculoEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);

        assertVeiculoFields(body);
    }


    @Test
    void delete_returnDeletedVeiculo_WhenSucessful() {
        ResponseEntity<String> veiculoEntity = veiculoController.deleteVeiculo(VALID_VEICULO.getId());

        Assertions.assertThat(veiculoEntity).isNotNull();
        Assertions.assertThat(veiculoEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
    }

    @Test
    void listAll_returnListofVeiculos_WhenSucessful() {
        ResponseEntity<List<Veiculo>> veiculoEntity = veiculoController.findAllVeiculos();
        List<Veiculo> body = veiculoEntity.getBody();

        Assertions.assertThat(veiculoEntity).isNotNull();
        Assertions.assertThat(body).isNotNull().isNotEmpty().hasSize(1).contains(VALID_VEICULO);
        Assertions.assertThat(veiculoEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
    }

    @Test
    void listAll_returnEmptyListofVeiculos_WhenSucessful() {
        when(veiculoService.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Veiculo>> veiculosEntity = veiculoController.findAllVeiculos();
        List<Veiculo> body = veiculosEntity.getBody();

        Assertions.assertThat(veiculosEntity).isNotNull();
        Assertions.assertThat(body).isNotNull().isEmpty();
        Assertions.assertThat(veiculosEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
    }

    @Test
    void findById_returnVeiculo_WhenSucessful() throws NotFoundException {
        ResponseEntity<Object> veiculoEntity = veiculoController.findVeiculoById(VALID_VEICULO.getId());
        Veiculo body = (Veiculo) veiculoEntity.getBody();

        Assertions.assertThat(veiculoEntity).isNotNull();
        Assertions.assertThat(body.getId()).isNotNull().isEqualTo(1);
        Assertions.assertThat(veiculoEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
    }

    @Test
    void findByPlaca_returnVeiculo_WhenSucessful() throws NotFoundException {
        ResponseEntity<Object> veiculoEntity = veiculoController.findVeiculoByPlaca(VALID_VEICULO.getPlaca());
        Veiculo body = (Veiculo) veiculoEntity.getBody();

        Assertions.assertThat(veiculoEntity).isNotNull();
        Assertions.assertThat((body.getPlaca())).isNotNull().isEqualTo("HTM5196"); //testando p da errado
        Assertions.assertThat(veiculoEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);

    }

    private void assertVeiculoFields(Veiculo veiculo) {
        Assertions.assertThat(veiculo.getMarca()).isNotNull().isNotEmpty();
        Assertions.assertThat(veiculo.getModelo()).isNotNull().isNotEmpty();
        Assertions.assertThat(veiculo.getCor()).isNotNull().isNotEmpty();
        Assertions.assertThat(veiculo.getPlaca()).isNotNull().isNotEmpty();
        Assertions.assertThat(veiculo.getTipo()).isNotNull();
    }
}
