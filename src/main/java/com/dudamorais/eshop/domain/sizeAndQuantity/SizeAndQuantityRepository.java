package com.dudamorais.eshop.domain.sizeAndQuantity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeAndQuantityRepository extends JpaRepository<SizeAndQuantity, UUID> {
   public Optional<ArrayList<SizeAndQuantity>> findAllByProductId(UUID productId);
}
