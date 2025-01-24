package com.dudamorais.eshop.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record EditProductDTO(UUID productId, String name, String description, double price, UUID type, List<SizeAndQuantityDTO> sizeAndQuantities, String mainPictureURL, ArrayList<String> otherPicturesURL, List<String> oldUrls) {
    
}
