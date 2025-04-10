package com.ads.demo.repository;

import com.ads.demo.model.entity.Pessoa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PessoaRepository {
    Pessoa salvar(Pessoa pessoa);
    Pessoa buscar(String id);
    List<Pessoa> listar();
    void deletar(String id);
}

