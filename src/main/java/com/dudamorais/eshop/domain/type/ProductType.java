package com.dudamorais.eshop.domain.type;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

@Entity
@Table
public class ProductType {
    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    public ProductType(String name){
        setName(name);
    }
}
