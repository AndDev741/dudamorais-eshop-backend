package com.dudamorais.eshop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dudamorais.eshop.domain.dto.CreateProductDTO;
import com.dudamorais.eshop.domain.dto.EditProductDTO;
import com.dudamorais.eshop.domain.sizeAndQuantity.SizeAndQuantity;
import com.dudamorais.eshop.exceptions.ProductNotFound;
import com.dudamorais.eshop.exceptions.UserNotFound;
import com.dudamorais.eshop.user.User;
import com.dudamorais.eshop.user.UserRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    public Product getProduct(UUID id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
    }

    public ArrayList<Product> getProducts(UUID userId){
        return productRepository.findByUserId(userId).orElseThrow(() -> new UserNotFound("User not found"));
    }

    public ResponseEntity<Map<String, String>> createProduct(CreateProductDTO createProductDTO){
        User user = userRepository.findById(createProductDTO.userId()).orElseThrow(() -> new UserNotFound("User not found"));

        try{
            Product newProduct = new Product(user, createProductDTO);
            
            List<SizeAndQuantity> sizesAndQuantities = createProductDTO.sizeAndQuantities().stream()
                .map(sizeAndQuantityDTO -> new SizeAndQuantity(
                    sizeAndQuantityDTO.size(),
                    sizeAndQuantityDTO.quantity(),
                    newProduct
                ))
                .collect(Collectors.toList());

            newProduct.setSizeAndQuantity(sizesAndQuantities);

            productRepository.save(newProduct);
            return ResponseEntity.ok().body(Map.of("success", "Product Created Successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> editProduct(EditProductDTO editProductDTO){
        Product editProduct = productRepository.findById(editProductDTO.productId()).orElseThrow(() -> new ProductNotFound("Product not found"));

        editProduct.setName(editProductDTO.name());
        editProduct.setDescription(editProductDTO.description());
        editProduct.setPrice(editProductDTO.price());
        editProduct.setType(editProductDTO.type());
        editProduct.setMainPictureUrl(editProductDTO.mainPictureURL());
        editProduct.setOtherPicturesUrl(editProductDTO.otherPicturesURL());

        try{
            productRepository.save(editProduct);
            return ResponseEntity.ok().body(Map.of("success", "Product edited successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> deleteProduct(UUID productId){
        Product product = getProduct(productId);

        try{
            productRepository.delete(product);
            return ResponseEntity.ok(Map.of("success", "Deleted successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
