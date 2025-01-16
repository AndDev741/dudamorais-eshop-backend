package com.dudamorais.eshop.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dudamorais.eshop.domain.type.CreateProductTypeDTO;
import com.dudamorais.eshop.domain.type.EditProductTypeDTO;
import com.dudamorais.eshop.domain.type.ProductType;
import com.dudamorais.eshop.domain.type.ProductTypeService;

@RestController
@RequestMapping("/type")
public class TypeController {
    private final ProductTypeService productTypeService;

    @Autowired
    public TypeController(ProductTypeService productTypeService){
        this.productTypeService = productTypeService;
    }

    @GetMapping(value = "/{userId}")
    public List<ProductType> getTypes(@PathVariable UUID userId){
        return productTypeService.getTypes(userId);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createType(@RequestBody CreateProductTypeDTO dto){
        return productTypeService.createType(dto);
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> editType(@RequestBody EditProductTypeDTO dto){
        return productTypeService.editType(dto);
    }

    @DeleteMapping(value = "/{typeId}")
    public ResponseEntity<Map<String, String>> deleteType(@PathVariable UUID typeId){
        return productTypeService.deleteType(typeId);
    }
}
