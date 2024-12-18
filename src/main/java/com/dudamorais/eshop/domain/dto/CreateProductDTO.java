package com.dudamorais.eshop.domain.dto;

import java.util.ArrayList;
import java.util.UUID;

public record CreateProductDTO(UUID userId, String name, String description, double price, String type, String mainPictureUrl, ArrayList<String> otherPicturesUrl) {
    
}
