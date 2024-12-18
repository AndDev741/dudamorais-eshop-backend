package com.dudamorais.eshop.domain.dto;

import java.util.ArrayList;
import java.util.UUID;

public record EditProductDTO(UUID productId, String name, String description, double price, String type, String mainPictureURL, ArrayList<String> otherPicturesURL) {
    
}
