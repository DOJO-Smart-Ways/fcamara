package com.estacionamento.restapi.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
@Entity
@Table(name = "veiculo")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "marca")
    private String marca;

    @Column(nullable = false, name = "modelo")
    private String modelo;

    @Column(nullable = false, name = "cor")
    private String cor;

    @Column(nullable = false, name = "placa", unique = true)
    private String placa;

    @Column(nullable = false, name = "tipoVeiculo")
    private TipoVeiculo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    @JsonIgnore
    private Estabelecimento estabelecimento;

    public Veiculo() {
    }

    public Veiculo(Integer id, String marca, String modelo, String cor, String placa, TipoVeiculo tipo, Estabelecimento estabelecimento) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.tipo = tipo;
        this.estabelecimento = estabelecimento;
    }

    public Veiculo(String marca, String modelo, String cor, String placa, TipoVeiculo tipo, Estabelecimento estabelecimento) {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.tipo = tipo;
        this.estabelecimento = estabelecimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculo tipo) {
        this.tipo = tipo;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}