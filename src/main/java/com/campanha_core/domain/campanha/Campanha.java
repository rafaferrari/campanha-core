package com.campanha_core.domain.campanha;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Campanha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long idTimeCoracao;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate dataInicial;

    @NotNull
    private LocalDate dataFinal;

    public Long getId() {
        return id;
    }

    public void setIdTimeCoracao(Long idTimeCoracao) {
        this.idTimeCoracao = idTimeCoracao;
    }

    public Long getIdTimeCoracao() {
        return idTimeCoracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public void setDataInicial(final LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataFinal(final LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

}
