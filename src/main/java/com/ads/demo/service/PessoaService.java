package com.ads.demo.service;

import com.ads.demo.model.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    Pessoa salvar(Pessoa pessoa);
    Pessoa buscar(String id);
    List<Pessoa> listar();
    void deletar(String id);
}

