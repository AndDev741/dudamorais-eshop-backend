package com.dudamorais.eshop.domain.type;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
    public Optional<List<ProductType>> findByUserId(UUID userId);
}
