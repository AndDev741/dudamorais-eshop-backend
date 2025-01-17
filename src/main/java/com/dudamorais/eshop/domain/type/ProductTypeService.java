package com.dudamorais.eshop.domain.type;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dudamorais.eshop.exceptions.TypeNotFound;
import com.dudamorais.eshop.exceptions.UserNotFound;
import com.dudamorais.eshop.user.User;
import com.dudamorais.eshop.user.UserRepository;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public ProductType getType(UUID typeId){
        return productTypeRepository.findById(typeId).orElseThrow(() -> new TypeNotFound("Product Type not found"));
    }

    public List<ProductType> getTypes(UUID userId){
        return productTypeRepository.findByUserId(userId).orElseThrow(() -> new TypeNotFound("Types not founded"));
    }

    public ResponseEntity<Map<String, String>> createType(CreateProductTypeDTO dto){
        try{
            User user = userRepository.findById(dto.userId()).orElseThrow(() -> new UserNotFound("User not found"));
            ProductType newType = new ProductType(dto.name(), user);
            productTypeRepository.save(newType);
            return ResponseEntity.ok().body(Map.of("success", "Type created Successfully"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> editType(EditProductTypeDTO dto){
        try{
            ProductType editType = getType(dto.typeId());

            editType.setName(dto.newName());

            productTypeRepository.save(editType);

            return ResponseEntity.ok(Map.of("success", "Type edited successfully"));
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
