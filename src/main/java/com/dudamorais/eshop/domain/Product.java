package com.dudamorais.eshop.domain;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dudamorais.eshop.domain.dto.CreateProductDTO;
import com.dudamorais.eshop.user.User;

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

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @UuidGenerator
    private UUID id;
    
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String mainPictureUrl;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private ArrayList<String> otherPicturesUrl;

    public Product(User user, CreateProductDTO createProductDTO){
        setUser(user);
        setName(createProductDTO.name());
        setDescription(createProductDTO.description());
        setPrice(createProductDTO.price());
        setType(createProductDTO.type());
        setMainPictureUrl(createProductDTO.mainPictureUrl());
        setOtherPicturesUrl(createProductDTO.otherPicturesUrl());
    }


}
