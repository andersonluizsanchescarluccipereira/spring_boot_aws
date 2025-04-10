package com.ads.demo.service;

import com.ads.demo.model.entity.Pessoa;
import com.ads.demo.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository repository;

    public PessoaServiceImpl(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        return repository.salvar(pessoa);
    }

    @Override
    public Pessoa buscar(String id) {
        return repository.buscar(id);
    }

    @Override
    public List<Pessoa> listar() {
        return repository.listar();
    }

    @Override
    public void deletar(String id) {
        repository.deletar(id);
    }
}