package com.dudamorais.eshop.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record CreateProductDTO(UUID userId, String name, String description, double price, UUID type, List<SizeAndQuantityDTO> sizeAndQuantities,String mainPictureUrl, ArrayList<String> otherPicturesUrl) {
    
}
