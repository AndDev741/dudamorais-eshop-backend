package com.dudamorais.eshop.domain;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    public Optional<ArrayList<Product>> findByUserId(UUID userId);
}
