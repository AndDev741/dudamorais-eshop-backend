package com.dudamorais.eshop.user;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dudamorais.eshop.domain.Product;
import com.dudamorais.eshop.user.DTO.UserRegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Product> products;

    public User(UserRegisterDTO dto){
        this.username = dto.username();
        this.password = dto.password();
    }
}
