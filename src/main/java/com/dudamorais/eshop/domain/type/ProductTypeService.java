package com.dudamorais.eshop.domain.type;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.dudamorais.eshop.domain.Product;
import com.dudamorais.eshop.domain.ProductService;
import com.dudamorais.eshop.exceptions.TypeNotFound;

public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductService productService;

    public ProductType getType(UUID typeId){
        return productTypeRepository.findById(typeId).orElseThrow(() -> new TypeNotFound("Product Type not found"));
    }

    public ArrayList<ProductType> getTypes(UUID productId){
        Product product = productService.getProduct(productId);

        return productTypeRepository.findByProductId(product.getType().getId()).orElseThrow(() -> new TypeNotFound("Types not founded"));
    }

    public ResponseEntity<Map<String, String>> createType(String name){
        try{
            ProductType newType = new ProductType(name);
            productTypeRepository.save(newType);
            return ResponseEntity.ok().body(Map.of("success", "Type created Successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> editType(UUID typeId, String newName){
        try{
            ProductType editType = getType(typeId);

            editType.setName(newName);

            productTypeRepository.save(editType);

            return ResponseEntity.ok(Map.of("sucess", "Type edited successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> deleteType(UUID typeId){
        try{
            ProductType deleteType = getType(typeId);
            productTypeRepository.delete(deleteType);
            
            return ResponseEntity.ok(Map.of("success", "Type deleted successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


}
