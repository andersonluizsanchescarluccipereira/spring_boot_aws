package com.ads.demo.application.models.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class CustomerEntity {

    private String id;
    private String name;
    private Integer age;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    // os setters são obrigatórios pelo SDK
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("nome")
    public String getName() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }

    @DynamoDbAttribute("idade")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public CustomerEntity() {
    }

    public CustomerEntity(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

