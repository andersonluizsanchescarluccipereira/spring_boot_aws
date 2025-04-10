package com.ads.demo.controller;

import com.ads.demo.model.entity.Pessoa;
import com.ads.demo.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody Pessoa pessoa) {
        Pessoa salva = service.salvar(pessoa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscar(@PathVariable String id) {
        Pessoa encontrada = service.buscar(id);
        return ResponseEntity.ok(encontrada);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar() {
        List<Pessoa> pessoas = service.listar();
        return ResponseEntity.ok(pessoas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}