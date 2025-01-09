package com.dudamorais.eshop.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.dudamorais.eshop.domain.Product;
import com.dudamorais.eshop.domain.ProductRepository;
import com.dudamorais.eshop.domain.ProductService;
import com.dudamorais.eshop.domain.dto.CreateProductDTO;
import com.dudamorais.eshop.domain.dto.EditProductDTO;
import com.dudamorais.eshop.domain.dto.SizeAndQuantityDTO;
import com.dudamorais.eshop.domain.sizeAndQuantity.SizeAndQuantity;
import com.dudamorais.eshop.domain.type.ProductType;
import com.dudamorais.eshop.exceptions.ProductNotFound;
import com.dudamorais.eshop.exceptions.UserNotFound;
import com.dudamorais.eshop.user.User;
import com.dudamorais.eshop.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    UUID productId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID typeId = UUID.randomUUID();

    ProductType type = new ProductType("type");
    List<SizeAndQuantity> sizeAndQuantities = new ArrayList<>(List.of());

    Product product = new Product(productId, "ProductName", "ProductDescription", 40.50, type, sizeAndQuantities, null, null, null);
    User user = new User(userId, "user", "123456", null);

    @Test
    public void shouldReturnAProductSuccessfully(){
        
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product productTest = productService.getProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        assertEquals(product, productTest);
        assertEquals(product.getName(), productTest.getName());
    }

    @Test
    public void shouldReturnAArrayListOfProducts(){
        UUID userId = UUID.randomUUID();
        ArrayList<Product> products = new ArrayList<>(List.of(product, product));

        when(productRepository.findByUserId(userId)).thenReturn(Optional.of(products));

        ArrayList<Product> assertProducts = productService.getProducts(userId);

        assertEquals(productId, assertProducts.get(0).getId());
        assertEquals(40.5, assertProducts.get(0).getPrice());
    }

    @Test
    public void shouldCreateSuccessfullyAProduct(){
        ArrayList<SizeAndQuantityDTO> sizeAndQuantityDTOs = new ArrayList<>();

        CreateProductDTO createProductDTO = new CreateProductDTO(userId, "product", "Product description", 50.60, type, sizeAndQuantityDTOs, "url.com", null);
        ResponseEntity<Map<String, String>> sucessResponse = ResponseEntity.ok().body(Map.of(
        "success", "Product Created Successfully"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<Map<String, String>> assertResponse = productService.createProduct(createProductDTO);

        assertEquals(sucessResponse.getStatusCode(), assertResponse.getStatusCode());
        assertEquals(sucessResponse.getBody(), assertResponse.getBody());
    }

    @Test
    public void shouldEditAProductSuccessfully(){
        EditProductDTO editProductDTO = new EditProductDTO(productId, "editedName", "editedDescription", 50.50, type, "newUrl.com", null);
        ResponseEntity<Map<String, String>> successResponse = ResponseEntity.ok().body(Map.of("success", "Product edited successfully"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        
        ResponseEntity<Map<String, String>> assertResponse = productService.editProduct(editProductDTO);

        assertEquals(successResponse.getStatusCode(), assertResponse.getStatusCode());
        assertEquals(successResponse.getBody(), assertResponse.getBody());
    }

    @Test
    public void shouldDeleteSuccessfullyAProduct(){
        ResponseEntity<Map<String, String>> successResponse = ResponseEntity.ok().body(Map.of("success", "Deleted successfully"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Map<String, String>> assertResponse = productService.deleteProduct(productId);

        assertEquals(successResponse.getStatusCode(), assertResponse.getStatusCode());
        assertEquals(successResponse.getBody(), assertResponse.getBody());
    }

    //Exceptions

    @Test
    public void shouldThrowAExceptionOfProductNotFound(){
        Exception exception = assertThrows(ProductNotFound.class, () -> {
            productService.getProduct(UUID.randomUUID());
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void shouldThrowAExceptionOfUserNotFound(){
        Exception exception = assertThrows(UserNotFound.class, () -> {
            productService.getProducts(UUID.randomUUID());
        });

        assertEquals("User not found", exception.getMessage());
    }
}
