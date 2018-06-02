package com.alpatech.cutgastos.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "eletrodomestico")
public class Eletrodomestico {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nome")
    private String nome;

    @Column(name="descricao")
    private String descricao;

    @Column(name="potencia_uso")
    private Integer potenciaUso;

    @Column(name="potencia_standby")
    private Integer potenciaStandby;

    @Column(name="data_cadastro")
    private Date dataCadastro;

    @Column(name="quantidade")
    private Integer quantidade;

    @Column(name="horas")
    private Integer horas;

    @Column(name="minutos")
    private Integer minutos;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPotenciaUso() {
        return potenciaUso;
    }

    public void setPotenciaUso(Integer potenciaUso) {
        this.potenciaUso = potenciaUso;
    }

    public Integer getPotenciaStandby() {
        return potenciaStandby;
    }

    public void setPotenciaStandby(Integer potenciaStandby) {
        this.potenciaStandby = potenciaStandby;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
