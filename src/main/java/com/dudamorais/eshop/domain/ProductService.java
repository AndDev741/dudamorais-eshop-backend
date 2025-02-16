package com.dudamorais.eshop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dudamorais.eshop.aws.AmazonS3Service;
import com.dudamorais.eshop.domain.dto.CreateProductDTO;
import com.dudamorais.eshop.domain.dto.DeleteProductDTO;
import com.dudamorais.eshop.domain.dto.EditProductDTO;
import com.dudamorais.eshop.domain.sizeAndQuantity.SizeAndQuantity;
import com.dudamorais.eshop.domain.type.ProductType;
import com.dudamorais.eshop.domain.type.ProductTypeRepository;
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

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;
    
    public Product getProduct(UUID id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
    }

    public ArrayList<Product> getProducts(UUID userId){
        return productRepository.findByUserId(userId).orElseThrow(() -> new UserNotFound("User not found"));
    }

    public ResponseEntity<Map<String, String>> createProduct(CreateProductDTO createProductDTO){
        User user = userRepository.findById(createProductDTO.userId()).orElseThrow(() -> new UserNotFound("User not found"));
        ProductType productType = productTypeRepository.findById(createProductDTO.type()).orElseThrow(() -> new ProductNotFound("type of product not found"));
        try{
            Product newProduct = new Product(user, createProductDTO, productType);
            
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
        ProductType productType = productTypeRepository.findById(editProductDTO.type()).orElseThrow(() -> new ProductNotFound("type of product not found"));

        if(editProductDTO.oldUrls().size() > 0){
            editProductDTO.oldUrls().forEach((oldUrl) -> {
                amazonS3Service.deleteFile(oldUrl);
            });
        }

        editProduct.setName(editProductDTO.name());
        editProduct.setDescription(editProductDTO.description());
        editProduct.setPrice(editProductDTO.price());
        editProduct.setType(productType);
        editProduct.setMainPictureUrl(editProductDTO.mainPictureURL());
        editProduct.setOtherPicturesUrl(editProductDTO.otherPicturesURL());

        editProduct.getSizeAndQuantity().clear();
        
        editProductDTO.sizeAndQuantities().forEach(sizeAndQuantityDTO -> {
            SizeAndQuantity sizeAndQuantity = new SizeAndQuantity(
                sizeAndQuantityDTO.size(),
                sizeAndQuantityDTO.quantity(),
                editProduct
            );
            editProduct.addSizeAndQuantity(sizeAndQuantity);
        });

        try{
            productRepository.save(editProduct);
            return ResponseEntity.ok().body(Map.of("success", "Product edited successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> deleteProduct(DeleteProductDTO dto){
        Product product = getProduct(dto.productId());
        dto.picturesToDelete().forEach((picture) -> {
            amazonS3Service.deleteFile(picture);
        });
        try{
            productRepository.delete(product);
            return ResponseEntity.ok(Map.of("success", "Deleted successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
