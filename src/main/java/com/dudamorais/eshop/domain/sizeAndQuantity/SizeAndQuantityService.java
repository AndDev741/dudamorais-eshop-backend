package com.dudamorais.eshop.domain.sizeAndQuantity;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dudamorais.eshop.domain.Product;
import com.dudamorais.eshop.domain.ProductService;
import com.dudamorais.eshop.exceptions.SizeAndQuantityNotFound;

@Service
public class SizeAndQuantityService {
    @Autowired
    SizeAndQuantityRepository sizeAndQuantityRepository;

    @Autowired
    ProductService productService;

    public SizeAndQuantity getSizeAndQuantity(UUID id){
        return sizeAndQuantityRepository.findById(id).orElseThrow(() -> new SizeAndQuantityNotFound("Size and Quantity not founded"));
    }

    public ArrayList<SizeAndQuantity> getSizesAndQuantities(UUID productId){
        return sizeAndQuantityRepository.findAllByProductId(productId).orElseThrow(() -> new SizeAndQuantityNotFound("Size and Quantity not founded for this product"));
    }

    public ResponseEntity<Map<String,String>> createSizeAndQuantity(int size, int quantity, Product product){
        SizeAndQuantity newSizeAndQuantity = new SizeAndQuantity(size, quantity, product);

        sizeAndQuantityRepository.save(newSizeAndQuantity);

        return ResponseEntity.ok().body(Map.of("success", "Size and Quantity created successfully"));
    }

    public ResponseEntity<Map<String, String>> editSizeAndQuantity(UUID id,Integer newQuantity){
        SizeAndQuantity sizeAndQuantity = getSizeAndQuantity(id);

        sizeAndQuantity.setQuantity(newQuantity);

        sizeAndQuantityRepository.save(sizeAndQuantity);

        return ResponseEntity.ok().body(Map.of("success", "Quantity edited successfully"));
    }

    public ResponseEntity<Map<String, String>> deleteSizeAndQuantity(UUID id){
        SizeAndQuantity sizeAndQuantityToDelete = getSizeAndQuantity(id); 

        sizeAndQuantityRepository.delete(sizeAndQuantityToDelete);

        return ResponseEntity.ok(Map.of("success", "Size and Quantity deleted successfully"));
    }


}
