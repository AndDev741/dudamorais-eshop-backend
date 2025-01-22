package com.dudamorais.eshop.domain.sizeAndQuantity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dudamorais.eshop.domain.Product;
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

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SizeAndQuantity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column
    private Integer size;

    @Column
    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public SizeAndQuantity(Integer size, Integer quantity, Product product){
        this.size = size;
        this.quantity = quantity;
        this.product = product;
    }


    
}
