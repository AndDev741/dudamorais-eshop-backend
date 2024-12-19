package com.dudamorais.eshop.domain.dto;

import java.util.ArrayList;
import java.util.UUID;

import com.dudamorais.eshop.domain.type.ProductType;

public record EditProductDTO(UUID productId, String name, String description, double price, ProductType type, String mainPictureURL, ArrayList<String> otherPicturesURL) {
    
}
