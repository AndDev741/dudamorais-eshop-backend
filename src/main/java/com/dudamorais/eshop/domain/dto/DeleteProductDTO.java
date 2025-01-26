package com.dudamorais.eshop.domain.dto;

import java.util.List;
import java.util.UUID;

public record DeleteProductDTO(UUID productId, List<String> picturesToDelete){
    
}
