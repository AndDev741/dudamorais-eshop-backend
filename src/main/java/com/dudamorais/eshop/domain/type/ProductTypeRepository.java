package com.dudamorais.eshop.domain.type;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
    public Optional<ArrayList<ProductType>> findByProductId(UUID productId);
}
