package com.dudamorais.eshop.domain.type;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dudamorais.eshop.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table
public class ProductType {
    @Id
    @UuidGenerator
    private UUID id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ProductType(String name){
        setName(name);
    }
}
