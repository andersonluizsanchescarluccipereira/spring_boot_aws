package com.ads.demo.repository;

import com.ads.demo.model.entity.Pessoa;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class PessoaRepositoryImpl implements PessoaRepository {

    private final DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<Pessoa> table;

    public PessoaRepositoryImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @PostConstruct
    public void init() {
        this.table = enhancedClient.table("global01", TableSchema.fromBean(Pessoa.class));
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        table.putItem(pessoa);
        return pessoa;
    }

    @Override
    public Pessoa buscar(String id) {
        return table.getItem(Key.builder().partitionValue(id).build());
    }

    @Override
    public List<Pessoa> listar() {
        return StreamSupport
                .stream(table.scan().items().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(String id) {
        table.deleteItem(Key.builder().partitionValue(id).build());
    }
}

