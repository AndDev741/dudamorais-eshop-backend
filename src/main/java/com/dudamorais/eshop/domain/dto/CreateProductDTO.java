package com.dudamorais.eshop.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dudamorais.eshop.domain.type.ProductType;

public record CreateProductDTO(UUID userId, String name, String description, double price, ProductType type, List<SizeAndQuantityDTO> sizeAndQuantities,String mainPictureUrl, ArrayList<String> otherPicturesUrl) {
    
}
